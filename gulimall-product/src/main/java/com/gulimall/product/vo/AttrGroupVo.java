package com.gulimall.product.vo;

import com.gulimall.common.valid.AddGroup;
import com.gulimall.common.valid.UpdateGroup;
import com.gulimall.common.valid.UpdateStatusGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;


/**
 * @author aqiang9  2020-07-30
 */
@Getter
@Setter
public class AttrGroupVo  {

    /**
     * 分组id
     */
    @Null(message = "属性分组id不能指定",groups = AddGroup.class)
    @NotNull(message = "分组id缺失",groups = {UpdateStatusGroup.class , UpdateGroup.class})
    private Long attrGroupId;
    /**
     * 组名
     */
    @NotBlank(message = "分组名缺失",groups = AddGroup.class)
    private String attrGroupName;
    /**
     * 排序
     */
    @PositiveOrZero(message = "排序必须大于等于0",groups = {AddGroup.class ,UpdateGroup.class})
    private Integer sort;
    /**
     * 描述
     */
    @NotBlank(message = "分组描述缺失",groups = {AddGroup.class , UpdateGroup.class})
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    @NotNull(message = "未指定分类",groups = {AddGroup.class,UpdateGroup.class})
    @Positive(message = "分类id必须大于0")
    private Long categoryId;

    /**
     * 所属 分类 的完整路径
     */
    private List<Long> categoryPath ;
}
