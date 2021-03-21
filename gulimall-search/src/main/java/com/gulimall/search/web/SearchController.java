package com.gulimall.search.web;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.vo.AttrRespVo;
import com.gulimall.search.feign.ProductFeignService;
import com.gulimall.search.service.MallSearchService;
import com.gulimall.search.vo.BrandVo;
import com.gulimall.search.vo.NavVo;
import com.gulimall.search.vo.SearchParam;
import com.gulimall.search.vo.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author aqiang9  2020-08-19
 */
@Controller
@Slf4j
public class SearchController {
    @Autowired
    MallSearchService mallSearchService;

    @Autowired
    ProductFeignService productFeignService;


    @GetMapping({"/", "list.html"})
    public String searchIndex(SearchParam searchParam, HttpServletRequest request, ModelMap modelMap) {
        System.out.println("请求参数：" + searchParam);
        if (searchParam.getPageNum() == 0) {
            searchParam.setPageNum(1);
        }
        SearchResult result = mallSearchService.search(searchParam);
        modelMap.put("result", result);
        modelMap.put("keyword" , searchParam.getKeyword() ) ;

        log.info(result.toString() );

        // 构造面包屑数据
//        属性
        List<NavVo> navVos = new ArrayList<>();
        List<String> attrs = searchParam.getAttrs();
        List<Long> attrIds = new ArrayList<>()  ;
        String queryString = request.getQueryString();
        log.info("---  请求参数 ： {} " , queryString);
        if (attrs != null && attrs.size() > 0) {
            navVos = attrs.stream().map(attr -> {
                NavVo navVo = new NavVo();
                String[] s = attr.split("_");

                navVo.setNavValue(s[1]);
                attrIds.add(Long.parseLong(s[0])) ;
                CommonResult<AttrRespVo> info = productFeignService.info(Long.parseLong(s[0]));
                if (info.getCode() == 0) {
                    navVo.setNavName(info.getData().getAttrName());
                } else {
                    navVo.setNavName(s[0]);
                }
                System.out.println("attr==="+ attr);
                String param = queryStringReplace(queryString, "attrs", attr);
                navVo.setLink("http://search.gulimall.com/list.html?" + param);
                return navVo;
            }).collect(Collectors.toList());
        }
//        品牌  会进行查到
        List<BrandVo> brands = result.getBrands();

        // TODO  只需要替换 搜索到的这个id
        if (searchParam.getBrandId() != null && brands != null && brands.size() > 0) {
            for (BrandVo brand : brands) {
                NavVo navVo = new NavVo();
                Long brandId = brand.getBrandId();
                String brandName = brand.getBrandName();
                navVo.setNavName("品牌");
                navVo.setNavValue(brandName);
                navVo.setLink(queryStringReplace(queryString, "brandId", brandId.toString() ));
                navVos.add(navVo) ;
            }
        }

// TODO  分类
        modelMap.put("navs", navVos);
        modelMap.put("attrIds", attrIds);
        return "list";
    }

    private String queryStringReplace(String queryString, String replaceKey, String replaceValue) {
        try {
            replaceValue = URLEncoder.encode(replaceValue , "UTF-8");
            replaceValue = replaceValue.replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            log.error("url编码异常，{}", e.getMessage());
            e.printStackTrace();
        }
        // 因为有 & 所以
        Pattern compile = Pattern.compile("&?" + replaceKey + "=" + replaceValue, Pattern.CASE_INSENSITIVE);

        // 链接  所有连接条件， 去掉当前
         return compile.matcher(queryString).replaceFirst("");
    }



}
