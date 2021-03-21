package com.gulimall.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页数据 基础 vo 对象
 *
 * @author aqiang9  2020-07-29
 */

@ApiModel
@Data
public class PageVo {
    /**
     * 当前页码
     */
    @ApiModelProperty(name = "page" , value = "当前页码")
    private Long page;
    /**
     * 每页显示记录数
     */

    @ApiModelProperty(name = "limit" , value = "每页显示记录数")
    private Long limit;
    /**
     * 排序字段
     */

    @ApiModelProperty(name = "sidx" , value = "排序字段")
    private String sidx;
    /**
     * 排序方式
     */

    @ApiModelProperty(name = "order" , value = "排序方式")
    private String order;
    /**
     * 升序
     */

    @ApiModelProperty(name = "asc" , value = "排序规则升序/降序" ,allowableValues = "asc,desc" )
    private String asc;

    /**
     * 查询关键字
     */
    @ApiModelProperty(name = "key" , value = "查询关键字" )
    private String key;
}
