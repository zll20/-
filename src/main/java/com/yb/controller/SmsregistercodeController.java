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

import com.yb.entity.SmsregistercodeEntity;
import com.yb.entity.view.SmsregistercodeView;

import com.yb.service.SmsregistercodeService;
import com.yb.utils.PageUtils;
import com.yb.utils.R;
import com.yb.utils.EncryptUtil;
import com.yb.utils.MPUtil;
import com.yb.utils.MapUtils;
import com.yb.utils.CommonUtil;
import java.io.IOException;

/**
 * 短信验证码
 * 后端接口
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
@RestController
@RequestMapping("/smsregistercode")
public class SmsregistercodeController {
    @Autowired
    private SmsregistercodeService smsregistercodeService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,SmsregistercodeEntity smsregistercode,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<SmsregistercodeEntity> ew = new QueryWrapper<SmsregistercodeEntity>();


        //查询结果
		PageUtils page = smsregistercodeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, smsregistercode), params), params));
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
    public R list(@RequestParam Map<String, Object> params,SmsregistercodeEntity smsregistercode,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<SmsregistercodeEntity> ew = new QueryWrapper<SmsregistercodeEntity>();

        //查询结果
		PageUtils page = smsregistercodeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, smsregistercode), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( SmsregistercodeEntity smsregistercode){
       	QueryWrapper<SmsregistercodeEntity> ew = new QueryWrapper<SmsregistercodeEntity>();
      	ew.allEq(MPUtil.allEQMapPre( smsregistercode, "smsregistercode"));
        return R.ok().put("data", smsregistercodeService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(SmsregistercodeEntity smsregistercode){
        QueryWrapper< SmsregistercodeEntity> ew = new QueryWrapper< SmsregistercodeEntity>();
 		ew.allEq(MPUtil.allEQMapPre( smsregistercode, "smsregistercode"));
		SmsregistercodeView smsregistercodeView =  smsregistercodeService.selectView(ew);
		return R.ok("查询短信验证码成功").put("data", smsregistercodeView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        SmsregistercodeEntity smsregistercode = smsregistercodeService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(smsregistercode,deSens);
        return R.ok().put("data", smsregistercode);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        SmsregistercodeEntity smsregistercode = smsregistercodeService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(smsregistercode,deSens);
        return R.ok().put("data", smsregistercode);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SmsregistercodeEntity smsregistercode, HttpServletRequest request){
        //ValidatorUtils.validateEntity(smsregistercode);
        smsregistercodeService.save(smsregistercode);
        return R.ok().put("data",smsregistercode.getId());
    }

    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody SmsregistercodeEntity smsregistercode, HttpServletRequest request){
        //ValidatorUtils.validateEntity(smsregistercode);
        smsregistercodeService.save(smsregistercode);
        return R.ok().put("data",smsregistercode.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        SmsregistercodeEntity smsregistercode = smsregistercodeService.getOne(new QueryWrapper<SmsregistercodeEntity>().eq("", username));
        return R.ok().put("data", smsregistercode);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody SmsregistercodeEntity smsregistercode, HttpServletRequest request){
        //ValidatorUtils.validateEntity(smsregistercode);
        //全部更新
        smsregistercodeService.updateById(smsregistercode);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        smsregistercodeService.removeBatchByIds(Arrays.asList(ids));
        return R.ok();
    }

	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,SmsregistercodeEntity smsregistercode, HttpServletRequest request,String pre){
        QueryWrapper<SmsregistercodeEntity> ew = new QueryWrapper<SmsregistercodeEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        // 组装参数
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        params.put("order", "desc");

		PageUtils page = smsregistercodeService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, smsregistercode), params), params));
        return R.ok().put("data", page);
    }




    // hasAlipay:$hasAlipay




}
