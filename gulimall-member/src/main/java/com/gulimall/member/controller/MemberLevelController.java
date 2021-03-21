package com.gulimall.member.controller;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.common.vo.PageVo;
import com.gulimall.member.entity.MemberLevelEntity;
import com.gulimall.member.service.MemberLevelService;
import com.gulimall.service.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;



/**
 * 会员等级
 *
 * @author aqiang9
 * @email ${email}
 * @date 2020-07-27 10:19:25
 */
@RestController
@RequestMapping("member/memberlevel")
public class MemberLevelController {
    @Autowired
    private MemberLevelService memberLevelService;

    /**
     * 列表
     */
    @GetMapping("/list")
//    //@RequiresPermissions("member:memberlevel:list")
    public CommonResult list(PageVo params){
        PageUtils page = memberLevelService.queryPage(params);
        return CommonResult.ok().data(page) ;
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//    //@RequiresPermissions("member:memberlevel:info")
    public CommonResult info(@PathVariable("id") Long id){
		MemberLevelEntity memberLevel = memberLevelService.getById(id);

        return CommonResult.ok().data( memberLevel);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:memberlevel:save")
    public R save(@RequestBody MemberLevelEntity memberLevel){
		memberLevelService.save(memberLevel);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions("member:memberlevel:update")
    public R update(@RequestBody MemberLevelEntity memberLevel){
		memberLevelService.updateById(memberLevel);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("member:memberlevel:delete")
    public R delete(@RequestBody Long[] ids){
		memberLevelService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
