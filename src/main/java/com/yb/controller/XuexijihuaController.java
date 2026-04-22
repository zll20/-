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

import com.yb.entity.XuexijihuaEntity;
import com.yb.entity.view.XuexijihuaView;
import com.yb.entity.XueshengEntity;
import com.yb.entity.TokenEntity;
import com.yb.service.XuexijihuaService;
import com.yb.service.XueshengService;
import com.yb.service.TokenService;
import com.yb.utils.PageUtils;
import com.yb.utils.R;
import com.yb.utils.EncryptUtil;
import com.yb.utils.MPUtil;
import com.yb.utils.MapUtils;
import com.yb.utils.CommonUtil;
import java.io.IOException;

@RestController
@RequestMapping("/xuexijihua")
public class XuexijihuaController {
    @Autowired
    private XuexijihuaService xuexijihuaService;

    @Autowired
    private XueshengService xueshengService;

    @Autowired
    private TokenService tokenService;

    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,XuexijihuaEntity xuexijihua,
		HttpServletRequest request){
        QueryWrapper<XuexijihuaEntity> ew = new QueryWrapper<XuexijihuaEntity>();

		PageUtils page = xuexijihuaService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xuexijihua), params), params));
        Map<String, String> deSens = new HashMap<>();
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,XuexijihuaEntity xuexijihua,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date riqistart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date riqiend,
                @RequestParam(required = false) Double xuexishizhangstart,
                @RequestParam(required = false) Double xuexishizhangend,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<XuexijihuaEntity> ew = new QueryWrapper<XuexijihuaEntity>();
        if(riqistart!=null) ew.ge("riqi", riqistart);
        if(riqiend!=null) ew.le("riqi", riqiend);
        if(xuexishizhangstart!=null) ew.ge("xuexishizhang", xuexishizhangstart);
        if(xuexishizhangend!=null) ew.le("xuexishizhang", xuexishizhangend);

        // 从 Token 中获取当前登录用户
        String token = request.getHeader("Token");
        if(StringUtils.isNotBlank(token)) {
            try {
                // 通过 TokenService 查询 token 实体
                TokenEntity tokenEntity = tokenService.getTokenEntity(token);
                if(tokenEntity != null) {
                    Long userId = tokenEntity.getUserid();
                    // 查询当前用户信息
                    XueshengEntity currentUser = xueshengService.getById(userId);
                    if(currentUser != null) {
                        // 同时匹配昵称和账号，只要有一个匹配即可
                        String nicheng = currentUser.getNicheng();
                        String zhanghao = currentUser.getZhanghao();
                        
                        if(StringUtils.isNotBlank(nicheng) || StringUtils.isNotBlank(zhanghao)) {
                            ew.and(wrapper -> {
                                if(StringUtils.isNotBlank(nicheng)) {
                                    wrapper.eq("xingming", nicheng);
                                }
                                if(StringUtils.isNotBlank(zhanghao)) {
                                    if(StringUtils.isNotBlank(nicheng)) {
                                        wrapper.or().eq("zhanghao", zhanghao);
                                    } else {
                                        wrapper.eq("zhanghao", zhanghao);
                                    }
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //查询结果
		PageUtils page = xuexijihuaService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xuexijihua), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( XuexijihuaEntity xuexijihua){
       	QueryWrapper<XuexijihuaEntity> ew = new QueryWrapper<XuexijihuaEntity>();
      	ew.allEq(MPUtil.allEQMapPre( xuexijihua, "xuexijihua"));
        return R.ok().put("data", xuexijihuaService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(XuexijihuaEntity xuexijihua){
        QueryWrapper< XuexijihuaEntity> ew = new QueryWrapper< XuexijihuaEntity>();
 		ew.allEq(MPUtil.allEQMapPre( xuexijihua, "xuexijihua"));
		XuexijihuaView xuexijihuaView =  xuexijihuaService.selectView(ew);
		return R.ok("查询学习计划成功").put("data", xuexijihuaView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        XuexijihuaEntity xuexijihua = xuexijihuaService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(xuexijihua,deSens);
        return R.ok().put("data", xuexijihua);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        XuexijihuaEntity xuexijihua = xuexijihuaService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(xuexijihua,deSens);
        return R.ok().put("data", xuexijihua);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody XuexijihuaEntity xuexijihua, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xuexijihua);
        xuexijihuaService.save(xuexijihua);
        return R.ok().put("data",xuexijihua.getId());
    }

    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody XuexijihuaEntity xuexijihua, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xuexijihua);
        xuexijihuaService.save(xuexijihua);
        return R.ok().put("data",xuexijihua.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody XuexijihuaEntity xuexijihua, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xuexijihua);
        //全部更新
        xuexijihuaService.updateById(xuexijihua);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        xuexijihuaService.removeBatchByIds(Arrays.asList(ids));
        return R.ok();
    }





    // hasAlipay:否




}
