package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.product.entity.SpuInfoEntity;
import com.gulimall.product.vo.SpuPageVo;
import com.gulimall.product.vo.SpuSaveVo;
import com.gulimall.service.utils.PageUtils;

/**
 * spu信息
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {


    void saveInfo(SpuSaveVo spuSaveVo);

    PageUtils queryPageOnCondition(SpuPageVo params);

    void up(Long spuId);

}

