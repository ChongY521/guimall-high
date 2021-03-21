package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.entity.SpuImagesEntity;
import com.gulimall.service.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * spu图片
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 通过spuId 保存图片
     * @param id 通过spuId
     * @param images 图片集
     */
    void saveImages(Long spuId, List<String> images);
}

