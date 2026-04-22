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

import com.yb.entity.ExampaperEntity;
import com.yb.entity.view.ExampaperView;

import com.yb.service.ExampaperService;
import com.yb.utils.PageUtils;
import com.yb.utils.R;
import com.yb.utils.EncryptUtil;
import com.yb.utils.MPUtil;
import com.yb.utils.MapUtils;
import com.yb.utils.CommonUtil;
import java.io.IOException;
import com.yb.service.ExamquestionService;
import com.yb.service.ExamrecordService;
import com.yb.entity.ExamquestionEntity;
import com.yb.entity.ExamquestionbankEntity;
import com.yb.service.ExamquestionbankService;
import com.yb.entity.ExamrecordEntity;

/**
 * 英语测试
 * 后端接口
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
@RestController
@RequestMapping("/exampaper")
public class ExampaperController {
    @Autowired
    private ExampaperService exampaperService;




    @Autowired
    private ExamquestionService examquestionService;
    @Autowired
    private ExamquestionbankService examquestionbankService;
    @Autowired
    private ExamrecordService examrecordService;






    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ExampaperEntity exampaper,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<ExampaperEntity> ew = new QueryWrapper<ExampaperEntity>();


        //查询结果
		PageUtils page = exampaperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, exampaper), params), params));
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
    public R list(@RequestParam Map<String, Object> params,ExampaperEntity exampaper,
                @RequestParam(required = false) Double timestart,
                @RequestParam(required = false) Double timeend,
                @RequestParam(required = false) Double examnumstart,
                @RequestParam(required = false) Double examnumend,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<ExampaperEntity> ew = new QueryWrapper<ExampaperEntity>();
        if(timestart!=null) ew.ge("time", timestart);
        if(timeend!=null) ew.le("time", timeend);
        if(examnumstart!=null) ew.ge("examnum", examnumstart);
        if(examnumend!=null) ew.le("examnum", examnumend);

        //查询结果
		PageUtils page = exampaperService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, exampaper), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ExampaperEntity exampaper){
       	QueryWrapper<ExampaperEntity> ew = new QueryWrapper<ExampaperEntity>();
      	ew.allEq(MPUtil.allEQMapPre( exampaper, "exampaper"));
        return R.ok().put("data", exampaperService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ExampaperEntity exampaper){
        QueryWrapper< ExampaperEntity> ew = new QueryWrapper< ExampaperEntity>();
 		ew.allEq(MPUtil.allEQMapPre( exampaper, "exampaper"));
		ExampaperView exampaperView =  exampaperService.selectView(ew);
		return R.ok("查询英语测试成功").put("data", exampaperView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ExampaperEntity exampaper = exampaperService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(exampaper,deSens);
        return R.ok().put("data", exampaper);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ExampaperEntity exampaper = exampaperService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(exampaper,deSens);
        return R.ok().put("data", exampaper);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ExampaperEntity exampaper, HttpServletRequest request){
        //ValidatorUtils.validateEntity(exampaper);
        exampaperService.save(exampaper);
        return R.ok().put("data",exampaper.getId());
    }

    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ExampaperEntity exampaper, HttpServletRequest request){
        //ValidatorUtils.validateEntity(exampaper);
        exampaperService.save(exampaper);
        return R.ok().put("data",exampaper.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ExampaperEntity exampaper, HttpServletRequest request){
        //ValidatorUtils.validateEntity(exampaper);
        //全部更新
        exampaperService.updateById(exampaper);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        exampaperService.removeBatchByIds(Arrays.asList(ids));
        return R.ok();
    }





    // hasAlipay:否



    /**
     * 组卷
     */
    @RequestMapping("/compose")
    public R compose(HttpServletRequest request,@RequestParam Long paperid, @RequestParam String papername, @RequestParam Integer radioNum,
        @RequestParam Integer multipleChoiceNum, @RequestParam Integer determineNum, @RequestParam Integer fillNum, @RequestParam Integer subjectivityNum, @RequestParam(required = false, defaultValue = "2") Integer level){
        //如果已存在考试记录，不能进行重新组卷
        if(examrecordService.count(new QueryWrapper<ExamrecordEntity>().eq("paperid", paperid))>0) {
            return R.error("已存在考试记录，无法重新组卷");
        }
        //组卷之前删除该试卷之前的所有题目
        examquestionService.removeByMap(new MapUtils().put("paperid", paperid));
        List<ExamquestionbankEntity> questionList = new ArrayList<ExamquestionbankEntity>();
        //单选题
        if(radioNum>0) {
            QueryWrapper<ExamquestionbankEntity> countEw0 = new QueryWrapper<ExamquestionbankEntity>().eq("type", 0);
            long radioSize = examquestionbankService.count(countEw0);
            if(radioSize<radioNum) {
                return R.error("单选题库不足");
            } else {
                QueryWrapper<ExamquestionbankEntity> ew = new QueryWrapper<ExamquestionbankEntity>();
                ew.eq("type", 0);
                switch ( level)
                {
                    case 1:
                        ew.orderByDesc("level");
                        break;
                    case 3:
                        ew.orderByAsc("level");
                        break;
                    default:
                        ew.orderByDesc("RAND()");
                        break;
                }
                ew.last("limit "+radioNum);
                List<ExamquestionbankEntity> radioList = examquestionbankService.list(ew);
                questionList.addAll(radioList);
            }
        }
        //多选题
        if(multipleChoiceNum>0) {
            QueryWrapper<ExamquestionbankEntity> countEw1 = new QueryWrapper<ExamquestionbankEntity>().eq("type", 1);
                        long multipleChoiceSize = examquestionbankService.count(countEw1);
            if(multipleChoiceSize<multipleChoiceNum) {
                return R.error("多选题库不足");
            } else {
                QueryWrapper<ExamquestionbankEntity> ew = new QueryWrapper<ExamquestionbankEntity>();
                ew.eq("type", 1);
                switch ( level)
                {
                    case 1:
                        ew.orderByDesc("level");
                        break;
                    case 3:
                        ew.orderByAsc("level");
                        break;
                    default:
                        ew.orderByDesc("RAND()");
                        break;
                }
                ew.last("limit "+multipleChoiceNum);
                List<ExamquestionbankEntity> multipleChoiceList = examquestionbankService.list(ew);
                questionList.addAll(multipleChoiceList);
            }
        }
        //判断题
        if(determineNum>0) {
            QueryWrapper<ExamquestionbankEntity> countEw2 = new QueryWrapper<ExamquestionbankEntity>().eq("type", 2);
                        long determineSize = examquestionbankService.count(countEw2);
            if(determineSize<determineNum) {
                return R.error("判断题库不足");
            } else {
                QueryWrapper<ExamquestionbankEntity> ew = new QueryWrapper<ExamquestionbankEntity>();
                ew.eq("type", 2);
                switch ( level)
                {
                    case 1:
                        ew.orderByDesc("level");
                        break;
                    case 3:
                        ew.orderByAsc("level");
                        break;
                    default:
                        ew.orderByDesc("RAND()");
                        break;
                }
                ew.last("limit "+determineNum);
                List<ExamquestionbankEntity> determineList = examquestionbankService.list(ew);
                questionList.addAll(determineList);
            }
        }
        //填空题
        if(fillNum>0) {
            QueryWrapper<ExamquestionbankEntity> countEw3 = new QueryWrapper<ExamquestionbankEntity>().eq("type", 3);
                        long fillSize = examquestionbankService.count(countEw3);
            if(fillSize<fillNum) {
                return R.error("填空题库不足");
            } else {
                QueryWrapper<ExamquestionbankEntity> ew = new QueryWrapper<ExamquestionbankEntity>();
                ew.eq("type", 3);
                switch ( level)
                {
                    case 1:
                        ew.orderByDesc("level");
                        break;
                    case 3:
                        ew.orderByAsc("level");
                        break;
                    default:
                        ew.orderByDesc("RAND()");
                        break;
                }
                ew.last("limit "+fillNum);
                List<ExamquestionbankEntity> fillList = examquestionbankService.list(ew);
                questionList.addAll(fillList);
            }
        }
        //主观题
        if(subjectivityNum>0) {
            QueryWrapper<ExamquestionbankEntity> countEw4 = new QueryWrapper<ExamquestionbankEntity>().eq("type", 4);
                        long subjectivitySize = examquestionbankService.count(countEw4);
            if(subjectivitySize<subjectivityNum) {
                return R.error("主观题库不足");
            } else {
                QueryWrapper<ExamquestionbankEntity> ew = new QueryWrapper<ExamquestionbankEntity>();
                ew.eq("type", 4);
                switch ( level)
                {
                    case 1:
                        ew.orderByDesc("level");
                        break;
                    case 3:
                        ew.orderByAsc("level");
                        break;
                    default:
                        ew.orderByDesc("RAND()");
                        break;
                }
                ew.last("limit "+subjectivityNum);
                List<ExamquestionbankEntity> subjectivityList = examquestionbankService.list(ew);
                questionList.addAll(subjectivityList);
            }
        }
        //将提取题目归集到试卷中并保存
        if(questionList!=null && questionList.size()>0) {
            long seq = 0;
            for(ExamquestionbankEntity q : questionList) {
                ExamquestionEntity examquestion = new ExamquestionEntity();
                org.springframework.beans.BeanUtils.copyProperties(q, examquestion);
                examquestion.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
                examquestion.setPaperid(paperid);
                examquestion.setPapername(papername);
                examquestion.setSequence(++seq);
                examquestionService.save(examquestion);
            }
        }
        return R.ok();
    }

}
