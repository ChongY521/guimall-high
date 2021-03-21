package com.gulimall.product.web;

import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.service.CategoryService;
import com.gulimall.product.vo.Category2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author aqiang9  2020-08-15
 */
@Controller
public class IndexController {

    @Autowired
    CategoryService categoryService;


    @GetMapping(value = {"/", "/index", "/index.html" })
    public String index(ModelMap modelMap , HttpSession session) {
        List<CategoryEntity> categoryEntities = categoryService.getCategoryLevel1();

        System.out.println(session );
        modelMap.put("categories", categoryEntities);
        return "index";
    }

    @GetMapping("/index/json/category.json")
    @ResponseBody
    public Map<String,List<Category2Vo>> categoryJson() {
       return categoryService.getCategoryJson();
    }
}
