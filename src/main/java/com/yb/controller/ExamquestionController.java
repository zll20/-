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

import com.yb.entity.ExamquestionEntity;
import com.yb.entity.view.ExamquestionView;

import com.yb.service.ExamquestionService;
import com.yb.utils.PageUtils;
import com.yb.utils.R;
import com.yb.utils.EncryptUtil;
import com.yb.utils.MPUtil;
import com.yb.utils.MapUtils;
import com.yb.utils.CommonUtil;
import java.io.IOException;

/**
 * 试题
 * 后端接口
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
@RestController
@RequestMapping("/examquestion")
public class ExamquestionController {
    @Autowired
    private ExamquestionService examquestionService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ExamquestionEntity examquestion,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<ExamquestionEntity> ew = new QueryWrapper<ExamquestionEntity>();


        //查询结果
		PageUtils page = examquestionService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examquestion), params), params));
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
    public R list(@RequestParam Map<String, Object> params,ExamquestionEntity examquestion,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<ExamquestionEntity> ew = new QueryWrapper<ExamquestionEntity>();

        //查询结果
		PageUtils page = examquestionService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examquestion), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ExamquestionEntity examquestion){
       	QueryWrapper<ExamquestionEntity> ew = new QueryWrapper<ExamquestionEntity>();
      	ew.allEq(MPUtil.allEQMapPre( examquestion, "examquestion"));
        return R.ok().put("data", examquestionService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExamquestionEntity examquestion){
        QueryWrapper< ExamquestionEntity> ew = new QueryWrapper< ExamquestionEntity>();
 		ew.allEq(MPUtil.allEQMapPre( examquestion, "examquestion"));
		ExamquestionView examquestionView =  examquestionService.selectView(ew);
		return R.ok("查询试题成功").put("data", examquestionView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ExamquestionEntity examquestion = examquestionService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(examquestion,deSens);
        return R.ok().put("data", examquestion);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExamquestionEntity examquestion = examquestionService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(examquestion,deSens);
        return R.ok().put("data", examquestion);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ExamquestionEntity examquestion, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examquestion);
        examquestionService.save(examquestion);
        return R.ok().put("data",examquestion.getId());
    }

    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ExamquestionEntity examquestion, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examquestion);
        examquestionService.save(examquestion);
        return R.ok().put("data",examquestion.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ExamquestionEntity examquestion, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examquestion);
        //全部更新
        examquestionService.updateById(examquestion);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        examquestionService.removeBatchByIds(Arrays.asList(ids));
        return R.ok();
    }





    // hasAlipay:否




}
