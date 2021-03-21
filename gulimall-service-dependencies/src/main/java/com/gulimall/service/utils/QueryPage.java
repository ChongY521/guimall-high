package com.gulimall.service.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulimall.common.vo.PageVo;
import com.gulimall.service.constant.Constant;
import com.gulimall.service.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

/**
 * @author aqiang9  2020-07-29
 */
public class QueryPage<T> {
    public IPage<T> getPage(PageVo pageParams) {
        return this.getPage(pageParams, null, false);
    }

    public IPage<T> getPage(PageVo pageParams, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        // 组装参数
        if (pageParams != null) {
            if (pageParams.getPage() != null) {
                curPage = pageParams.getPage() ;
            }
            if (pageParams.getLimit() !=null ){
                limit = pageParams.getLimit() ;
            }
        }
        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //分页参数
        // params.put(Constant.PAGE, page);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        if (pageParams != null ) {
            String orderField = SQLFilter.sqlInject(pageParams.getSidx());
            String order = pageParams.getOrder() ;

            //前端字段排序
            if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
                if (Constant.ASC.equalsIgnoreCase(order)) {
                    return page.addOrder(OrderItem.asc(orderField));
                } else {
                    return page.addOrder(OrderItem.desc(orderField));
                }
            }
        }
        //没有排序字段，则不排序
        if (StringUtils.isBlank(defaultOrderField)) {
            return page;
        }

        //默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }
        return page;
    }
}
