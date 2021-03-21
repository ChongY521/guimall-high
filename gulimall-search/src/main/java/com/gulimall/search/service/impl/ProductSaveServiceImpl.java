package com.gulimall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.search.config.MyElasticsearchConfig;
import com.gulimall.search.constant.EsConstant;
import com.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author aqiang9  2020-08-15
 */
@Service
@Slf4j
public class ProductSaveServiceImpl implements ProductSaveService {
    @Autowired
    RestHighLevelClient client ;

    @Override
    public Boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {

//        1、建立索引
        BulkRequest bulkRequest = new BulkRequest();


        for (SkuEsModel skuEsModel : skuEsModels) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
//            indexRequest.id() ;
            indexRequest.id(skuEsModel.getSkuId().toString());

            indexRequest.source(JSON.toJSONString(skuEsModel) , XContentType.JSON) ;

            bulkRequest.add(indexRequest) ;
        }
        // 2、批量保存
        BulkResponse bulk = client.bulk(bulkRequest, MyElasticsearchConfig.COMMON_OPTIONS);
        boolean b = bulk.hasFailures();

        if (b){
            List<String> collect = Arrays.stream(bulk.getItems()).map(BulkItemResponse::getId).collect(Collectors.toList());
            log.error("商品上架错误, : {}" , collect);
            return false;
        }

        return true ;
    }
}
