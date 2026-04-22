package com.yb.controller;

import com.yb.annotation.IgnoreAuth;
import com.yb.entity.TingliEntity;
import com.yb.service.TingliService;
import com.yb.utils.PageUtils;
import com.yb.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("tingli")
public class TingliController {
    @Autowired
    private TingliService tingliService;

    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tingliService.queryPage(params);
        return R.ok().put("data", page);
    }

    @IgnoreAuth
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TingliEntity tingli = tingliService.getById(id);
        return R.ok().put("tingli", tingli);
    }

    @RequestMapping("/save")
    public R save(@RequestBody TingliEntity tingli){
        tingliService.save(tingli);
        return R.ok();
    }

    @RequestMapping("/update")
    public R update(@RequestBody TingliEntity tingli){
        tingliService.updateById(tingli);
        return R.ok();
    }

    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        tingliService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
