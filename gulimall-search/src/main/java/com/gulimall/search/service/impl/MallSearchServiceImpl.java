package com.gulimall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.search.vo.AttrVo ;
import com.gulimall.search.config.MyElasticsearchConfig;
import com.gulimall.search.constant.EsConstant;
import com.gulimall.search.service.MallSearchService;
import com.gulimall.search.vo.BrandVo;
import com.gulimall.search.vo.CategoryVo;
import com.gulimall.search.vo.SearchParam;
import com.gulimall.search.vo.SearchResult;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author aqiang9  2020-08-19
 */
@Service
public class MallSearchServiceImpl implements MallSearchService {
    @Autowired
    RestHighLevelClient client;

    @Override
    public SearchResult search(SearchParam searchParam) {
        SearchResult searchResult = null;
//        1、动态构建DSL 语句
        SearchRequest searchRequest = buildSearchRequest(searchParam);

        try {
            // 执行
            SearchResponse searchResponse = client.search(searchRequest, MyElasticsearchConfig.COMMON_OPTIONS);
//            2、分析响应数据 、封装数据
            searchResult = buildSearchResult(searchResponse, searchParam);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResult;
    }


    /**
     * 准备检索请求
     * 模糊匹配、过滤（属性、分类、品牌、价格区间、库存）排序、分页、高亮、聚合分析
     *
     * @param searchParam 检索参数
     * @return SearchRequest
     */
    private SearchRequest buildSearchRequest(SearchParam searchParam) {
//        构建DSL 对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        模糊匹配、过滤（属性、分类、品牌、价格区间、库存）
//        bool-query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//              must:
        if (!StringUtils.isEmpty(searchParam.getKeyword())) {
            boolQuery.must(QueryBuilders.matchQuery("skuTitle", searchParam.getKeyword()));

            // 高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            searchSourceBuilder.highlighter(highlightBuilder);
        }
//              filter:
//        List<QueryBuilder> filter = boolQuery.filter();
//            2.1 三级分类id
        if (searchParam.getCategory3Id() != null) {
            boolQuery.filter(QueryBuilders.termQuery("categoryId", searchParam.getCategory3Id()));
        }
//          2.2
        if (searchParam.getBrandId() != null && searchParam.getBrandId().size() > 0) {
            boolQuery.filter(QueryBuilders.termsQuery("brandId", searchParam.getBrandId()));
        }
//        2.3 按属性查询
        if (searchParam.getAttrs() != null && searchParam.getAttrs().size() > 0) {
            // attrs=1_5寸:8寸
            for (String attrStr : searchParam.getAttrs()) {
                BoolQueryBuilder nestBoolQuery = QueryBuilders.boolQuery();

                String[] s = attrStr.split("_");
                if (s.length == 2) {
                    String[] attrValues = s[1].split(":");
                    nestBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", s[0])); // 属性id
                    nestBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues)); // 属性值
                }
                // 每一个都 要生成一个嵌入式的查询
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestBoolQuery, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            }
        }
//        TODO 默认全部   需要修改 按库存
        if (searchParam.getHasStock() !=null &&  searchParam.getHasStock() == 1 ){
            boolQuery.filter(QueryBuilders.termQuery("hasStock", true ));
        }
//        价格区间
        if (!StringUtils.isEmpty(searchParam.getSkuPrice())) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");

            String price = searchParam.getSkuPrice();
            String[] s = price.split("_");

            if (s.length == 2) {
                rangeQuery.gte(s[0]);
                rangeQuery.lte(s[1]);
            } else if (s.length == 1) {
                if (price.startsWith("_")) {
                    rangeQuery.lte(s[0]);
                } else {
                    rangeQuery.gte(s[0]);
                }
            }
            boolQuery.filter(rangeQuery);
        }
        searchSourceBuilder.query(boolQuery);
//        排序、分页、高亮、

//        排序
        String sort = searchParam.getSort();
        if (!StringUtils.isEmpty(sort)) {
            String[] s = sort.split("_");
            if (s.length > 1 ){
                FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(s[0]);
                if ("desc".equals(s[1])){
                    searchSourceBuilder.sort(SortBuilders.fieldSort(s[0]).order(SortOrder.DESC)) ;
                }else if ("asc".equals(s[1])){
                    searchSourceBuilder.sort(SortBuilders.fieldSort(s[0]).order(SortOrder.ASC)) ;
                }
            }
        }

//        默认第一页
        searchSourceBuilder.from((searchParam.getPageNum() - 1) * EsConstant.PRODUCT_PAGE_SIZE);
        searchSourceBuilder.size(EsConstant.PRODUCT_PAGE_SIZE);
//      高亮 关键字的位置写 。。



        // 聚合分析
//        品牌聚合
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg").field("brandId").size(50);
        brandAgg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brandAgg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
        searchSourceBuilder.aggregation(brandAgg);
//        分类聚合
        TermsAggregationBuilder categoryAgg = AggregationBuilders.terms("category_agg").field("categoryId").size(20);
        categoryAgg.subAggregation(AggregationBuilders.terms("category_name_agg").field("categoryName").size(1));
        searchSourceBuilder.aggregation(categoryAgg);
//     属性聚合

        NestedAggregationBuilder attrAgg = AggregationBuilders.nested("attr_agg", "attrs");
        TermsAggregationBuilder attrIdAgg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId");

        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(30));

        attrAgg.subAggregation(attrIdAgg);
        searchSourceBuilder.aggregation(attrAgg);

        System.out.println("DSL 语句:");
        System.out.println(searchSourceBuilder.toString());

        return new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, searchSourceBuilder);
    }

    /**
     * 封装检索 结果
     *
     * @param searchResponse 查询结果
     */
    private SearchResult buildSearchResult(SearchResponse searchResponse, SearchParam searchParam) {
        SearchResult result = new SearchResult();
        // 1、查询到的商品
        SearchHits hits = searchResponse.getHits();
        ArrayList<SkuEsModel> esModels = new ArrayList<>();
        if (hits != null && hits.getHits().length > 0) {
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                SkuEsModel skuEsModel = JSON.parseObject(sourceAsString, SkuEsModel.class);


                if (!StringUtils.isEmpty(searchParam.getKeyword())) {
//                配置高亮
                    HighlightField titleHilght = hit.getHighlightFields().get("skuTitle");
                    String skuTitle = titleHilght.getFragments()[0].string();
                    skuEsModel.setSkuTitle(skuTitle);
                }
                esModels.add(skuEsModel);
            }
        }
        result.setProducts(esModels);

        // 聚合信息
        Aggregations aggregations = searchResponse.getAggregations();
        // 2、当前所涉及到的所有属性信息
        List<AttrVo> attrs = new ArrayList<>();
        ParsedNested attrAgg = aggregations.get("attr_agg");
        ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attr_id_agg");
        if (attrIdAgg.getBuckets() != null && attrIdAgg.getBuckets().size() > 0) {
            for (Terms.Bucket bucket : attrIdAgg.getBuckets()) {
                AttrVo attr = new AttrVo();
                // id
                attr.setAttrId(bucket.getKeyAsNumber().longValue());
                // name
                String attrName = ((ParsedStringTerms) bucket.getAggregations().get("attr_name_agg")).getBuckets().get(0).getKeyAsString();
                attr.setAttrName(attrName);

                List<String> attrValues = ((ParsedStringTerms) bucket.getAggregations().get("attr_value_agg")).getBuckets()
                        .stream().map(MultiBucketsAggregation.Bucket::getKeyAsString)
                        .collect(Collectors.toList());

                attr.setAttrValue(attrValues);
                attrs.add(attr);
            }
        }
        result.setAttrs(attrs);
//        3、封装品牌信息
        ParsedLongTerms brandAgg = aggregations.get("brand_agg");
        List<BrandVo> brands = new ArrayList<>();
        for (Terms.Bucket bucket : brandAgg.getBuckets()) {
//            品牌id
            long brandId = bucket.getKeyAsNumber().longValue();
//            品牌名
            String brandName = ((ParsedStringTerms) bucket.getAggregations().get("brand_name_agg")).getBuckets().get(0).getKeyAsString();
//            品牌图片
            String brandImg = ((ParsedStringTerms) bucket.getAggregations().get("brand_img_agg")).getBuckets().get(0).getKeyAsString();
            BrandVo brand = new BrandVo(brandId, brandName, brandImg);
            brands.add(brand);
        }
        result.setBrands(brands);

//        4、封装分类信息
        ParsedLongTerms categoryAgg = aggregations.get("category_agg");
        List<CategoryVo> categories = new ArrayList<>();
        for (Terms.Bucket bucket : categoryAgg.getBuckets()) {
            long categoryId = bucket.getKeyAsNumber().longValue();
            String categoryName = ((ParsedStringTerms) bucket.getAggregations().get("category_name_agg")).getBuckets().get(0).getKeyAsString();
            CategoryVo categoryVo = new CategoryVo(categoryId, categoryName);
            categories.add(categoryVo);
        }
        result.setCategories(categories);

// ======聚合信息完成=====
//        5、分页信息-页码
        result.setPageNum(searchParam.getPageNum());
//               总记录数
        long total = hits.getTotalHits().value;
        result.setTotal(total);
//        总页数
        long totalPage = total % EsConstant.PRODUCT_PAGE_SIZE == 0 ? total / EsConstant.PRODUCT_PAGE_SIZE : ((total / EsConstant.PRODUCT_PAGE_SIZE) + 1);
        result.setTotalPages((int) totalPage);

        return result;
    }
}
