package com.yb.controller;

import com.yb.annotation.IgnoreAuth;
import com.yb.entity.WenkanEntity;
import com.yb.service.WenkanService;
import com.yb.utils.PageUtils;
import com.yb.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("wenkan")
public class WenkanController {
    @Autowired
    private WenkanService wenkanService;

    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wenkanService.queryPage(params);
        return R.ok().put("data", page);
    }

    @IgnoreAuth
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        WenkanEntity wenkan = wenkanService.getById(id);
        return R.ok().put("data", wenkan);
    }

    @RequestMapping("/save")
    public R save(@RequestBody WenkanEntity wenkan){
        wenkanService.save(wenkan);
        return R.ok();
    }

    @RequestMapping("/update")
    public R update(@RequestBody WenkanEntity wenkan){
        wenkanService.updateById(wenkan);
        return R.ok();
    }

    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        wenkanService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
