package com.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.vo.PageVo;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.ware.entity.WareInfoEntity;

/**
 * 仓库信息
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-09 10:07:54
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(PageVo params);
}

