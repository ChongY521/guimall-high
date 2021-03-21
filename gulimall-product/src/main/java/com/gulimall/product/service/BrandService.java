package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.vo.BrandVo;
import com.gulimall.service.utils.PageUtils;

/**
 * 品牌
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(PageVo pageParams);

    /**
     * 更新详细信息
     * @param brandVo 前端传输的数据
     */
    void updateDetail(BrandVo brandVo);
}

