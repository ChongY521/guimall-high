package com.gulimall.search.service;

import com.gulimall.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author aqiang9  2020-08-15
 */

public interface ProductSaveService {
    Boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
