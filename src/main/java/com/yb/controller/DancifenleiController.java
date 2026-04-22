package com.yb.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.lang.*;
import java.math.*;
import com.yb.utils.*;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.yb.annotation.IgnoreAuth;

import com.yb.entity.DancifenleiEntity;
import com.yb.entity.view.DancifenleiView;

import com.yb.service.DancifenleiService;
import com.yb.utils.PageUtils;
import com.yb.utils.R;
import com.yb.utils.EncryptUtil;
import com.yb.utils.MPUtil;
import com.yb.utils.MapUtils;
import com.yb.utils.CommonUtil;
import java.io.IOException;

/**
 * 单词分类
 * 后端接口
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
@RestController
@RequestMapping("/dancifenlei")
public class DancifenleiController {
    @Autowired
    private DancifenleiService dancifenleiService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,DancifenleiEntity dancifenlei,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<DancifenleiEntity> ew = new QueryWrapper<DancifenleiEntity>();


        //查询结果
		PageUtils page = dancifenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, dancifenlei), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,DancifenleiEntity dancifenlei,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<DancifenleiEntity> ew = new QueryWrapper<DancifenleiEntity>();

        //查询结果
		PageUtils page = dancifenleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, dancifenlei), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DancifenleiEntity dancifenlei){
       	QueryWrapper<DancifenleiEntity> ew = new QueryWrapper<DancifenleiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( dancifenlei, "dancifenlei"));
        return R.ok().put("data", dancifenleiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DancifenleiEntity dancifenlei){
        QueryWrapper< DancifenleiEntity> ew = new QueryWrapper< DancifenleiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( dancifenlei, "dancifenlei"));
		DancifenleiView dancifenleiView =  dancifenleiService.selectView(ew);
		return R.ok("查询单词分类成功").put("data", dancifenleiView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DancifenleiEntity dancifenlei = dancifenleiService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(dancifenlei,deSens);
        return R.ok().put("data", dancifenlei);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DancifenleiEntity dancifenlei = dancifenleiService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(dancifenlei,deSens);
        return R.ok().put("data", dancifenlei);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DancifenleiEntity dancifenlei, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(dancifenleiService.count(new QueryWrapper<DancifenleiEntity>().eq("dancifenlei", dancifenlei.getDancifenlei()))>0) {
            return R.error("单词分类已存在");
        }
        //ValidatorUtils.validateEntity(dancifenlei);
        dancifenleiService.save(dancifenlei);
        return R.ok().put("data",dancifenlei.getId());
    }

    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody DancifenleiEntity dancifenlei, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(dancifenleiService.count(new QueryWrapper<DancifenleiEntity>().eq("dancifenlei", dancifenlei.getDancifenlei()))>0) {
            return R.error("单词分类已存在");
        }
        //ValidatorUtils.validateEntity(dancifenlei);
        dancifenleiService.save(dancifenlei);
        return R.ok().put("data",dancifenlei.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody DancifenleiEntity dancifenlei, HttpServletRequest request){
        //ValidatorUtils.validateEntity(dancifenlei);
        //验证字段唯一性，否则返回错误信息
        if(dancifenleiService.count(new QueryWrapper<DancifenleiEntity>().ne("id", dancifenlei.getId()).eq("dancifenlei", dancifenlei.getDancifenlei()))>0) {
            return R.error("单词分类已存在");
        }
        //全部更新
        dancifenleiService.updateById(dancifenlei);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        dancifenleiService.removeBatchByIds(Arrays.asList(ids));
        return R.ok();
    }





    // hasAlipay:否




}
