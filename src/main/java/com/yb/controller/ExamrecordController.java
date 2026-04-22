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

import com.yb.entity.ExamrecordEntity;
import com.yb.entity.view.ExamrecordView;

import com.yb.service.ExamrecordService;
import com.yb.utils.PageUtils;
import com.yb.utils.R;
import com.yb.utils.EncryptUtil;
import com.yb.utils.MPUtil;
import com.yb.utils.MapUtils;
import com.yb.utils.CommonUtil;
import java.io.IOException;

/**
 * 考试记录
 * 后端接口
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
@RestController
@RequestMapping("/examrecord")
public class ExamrecordController {
    @Autowired
    private ExamrecordService examrecordService;








    /**
     * 考试记录接口
     */
    @RequestMapping("/groupby")
    public R page2(@RequestParam Map<String, Object> params,ExamrecordEntity examrecord, HttpServletRequest request){
        if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
            examrecord.setUserid((Long)request.getSession().getAttribute("userId"));
        }
        QueryWrapper<ExamrecordEntity> ew = new QueryWrapper<ExamrecordEntity>();
		PageUtils page = examrecordService.queryPageGroupBy(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examrecord), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 选项统计接口
     */
    @RequestMapping("/options/num")
    public R optionsNum(@RequestParam Map<String, Object> params,ExamrecordEntity examrecord, HttpServletRequest request){
        QueryWrapper<ExamrecordEntity> ew = new QueryWrapper<ExamrecordEntity>();
        PageUtils page = examrecordService.queryPageOptionsNum(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examrecord), params), params));
        return R.ok().put("data", page);
    }


    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ExamrecordEntity examrecord,
		HttpServletRequest request){
        if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
            examrecord.setUserid((Long)request.getSession().getAttribute("userId"));
        }
        //设置查询条件
        QueryWrapper<ExamrecordEntity> ew = new QueryWrapper<ExamrecordEntity>();


        //查询结果
		PageUtils page = examrecordService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examrecord), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 前台列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ExamrecordEntity examrecord,
		HttpServletRequest request){
    	if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
    		examrecord.setUserid((Long)request.getSession().getAttribute("userId"));
    	}
        //设置查询条件
        QueryWrapper<ExamrecordEntity> ew = new QueryWrapper<ExamrecordEntity>();

        //查询结果
		PageUtils page = examrecordService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, examrecord), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ExamrecordEntity examrecord){
       	QueryWrapper<ExamrecordEntity> ew = new QueryWrapper<ExamrecordEntity>();
      	ew.allEq(MPUtil.allEQMapPre( examrecord, "examrecord"));
        return R.ok().put("data", examrecordService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExamrecordEntity examrecord){
        QueryWrapper< ExamrecordEntity> ew = new QueryWrapper< ExamrecordEntity>();
 		ew.allEq(MPUtil.allEQMapPre( examrecord, "examrecord"));
		ExamrecordView examrecordView =  examrecordService.selectView(ew);
		return R.ok("查询考试记录成功").put("data", examrecordView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ExamrecordEntity examrecord = examrecordService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(examrecord,deSens);
        return R.ok().put("data", examrecord);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExamrecordEntity examrecord = examrecordService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(examrecord,deSens);
        return R.ok().put("data", examrecord);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ExamrecordEntity examrecord, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examrecord);
        if(examrecord.getUserid()==null){
            examrecord.setUserid((Long) request.getSession().getAttribute("userId"));
        }
        examrecordService.save(examrecord);
        return R.ok().put("data",examrecord.getId());
    }

    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ExamrecordEntity examrecord, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examrecord);
    	examrecord.setUserid((Long)request.getSession().getAttribute("userId"));
        examrecordService.save(examrecord);
        return R.ok().put("data",examrecord.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ExamrecordEntity examrecord, HttpServletRequest request){
        //ValidatorUtils.validateEntity(examrecord);
        //全部更新
        examrecordService.updateById(examrecord);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        examrecordService.removeBatchByIds(Arrays.asList(ids));
        return R.ok();
    }




    /**
     * 当重新考试时，删除考生的某个试卷的所有考试记录
     */
    @RequestMapping("/deleteRecords")
    public R deleteRecords(@RequestParam Long userid,@RequestParam Long paperid){
    	examrecordService.remove(new QueryWrapper<ExamrecordEntity>().eq("paperid", paperid).eq("userid", userid));
        return R.ok();
    }

    // hasAlipay:否




}
