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

import com.yb.entity.ExamquestionbankEntity;
import com.yb.entity.view.ExamquestionbankView;

import com.yb.service.ExamquestionbankService;
import com.yb.utils.PageUtils;
import com.yb.utils.R;
import com.yb.utils.EncryptUtil;
import com.yb.utils.MPUtil;
import com.yb.utils.MapUtils;
import com.yb.utils.CommonUtil;
import java.io.IOException;

/**
 * 试题库
 * 后端接口
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
@RestController
@RequestMapping("/examquestionbank")
public class ExamquestionbankController {
    @Autowired
    private ExamquestionbankService examquestionbankService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ExamquestionbankEntity examquestionbank,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<ExamquestionbankEntity> ew = new QueryWrapper<ExamquestionbankEntity>();


        //查询结果
		PageUtils page = examquestionbankService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examquestionbank), params), params));
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
    public R list(@RequestParam Map<String, Object> params,ExamquestionbankEntity examquestionbank,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<ExamquestionbankEntity> ew = new QueryWrapper<ExamquestionbankEntity>();

        //查询结果
		PageUtils page = examquestionbankService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examquestionbank), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ExamquestionbankEntity examquestionbank){
       	QueryWrapper<ExamquestionbankEntity> ew = new QueryWrapper<ExamquestionbankEntity>();
      	ew.allEq(MPUtil.allEQMapPre( examquestionbank, "examquestionbank"));
        return R.ok().put("data", examquestionbankService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExamquestionbankEntity examquestionbank){
        QueryWrapper< ExamquestionbankEntity> ew = new QueryWrapper< ExamquestionbankEntity>();
 		ew.allEq(MPUtil.allEQMapPre( examquestionbank, "examquestionbank"));
		ExamquestionbankView examquestionbankView =  examquestionbankService.selectView(ew);
		return R.ok("查询试题库成功").put("data", examquestionbankView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ExamquestionbankEntity examquestionbank = examquestionbankService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(examquestionbank,deSens);
        return R.ok().put("data", examquestionbank);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExamquestionbankEntity examquestionbank = examquestionbankService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(examquestionbank,deSens);
        return R.ok().put("data", examquestionbank);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ExamquestionbankEntity examquestionbank, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examquestionbank);
        examquestionbankService.save(examquestionbank);
        return R.ok().put("data",examquestionbank.getId());
    }

    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ExamquestionbankEntity examquestionbank, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examquestionbank);
        examquestionbankService.save(examquestionbank);
        return R.ok().put("data",examquestionbank.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ExamquestionbankEntity examquestionbank, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examquestionbank);
        //全部更新
        examquestionbankService.updateById(examquestionbank);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        examquestionbankService.removeBatchByIds(Arrays.asList(ids));
        return R.ok();
    }





    // hasAlipay:否




}
