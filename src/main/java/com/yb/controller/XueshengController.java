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
import com.yb.service.TokenService;
import com.yb.entity.TokenEntity;
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

import com.yb.entity.XueshengEntity;
import com.yb.entity.view.XueshengView;

import com.yb.service.XueshengService;
import com.yb.utils.PageUtils;
import com.yb.utils.R;
import com.yb.utils.EncryptUtil;
import com.yb.utils.MPUtil;
import com.yb.utils.MapUtils;
import com.yb.utils.CommonUtil;
import java.io.IOException;
import com.yb.service.SmsregistercodeService;
import com.yb.entity.SmsregistercodeEntity;

/**
 * 学生
 * 后端接口
 * @author 
 * @email 
 * @date 2026-03-14 23:57:23
 */
@RestController
@RequestMapping("/xuesheng")
public class XueshengController {
    @Autowired
    private XueshengService xueshengService;
    @Autowired
    private SmsregistercodeService smsregistercodeService;





	@Autowired
	private TokenService tokenService;

	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
        XueshengEntity u = xueshengService.getOne(new QueryWrapper<XueshengEntity>().eq("zhanghao", username));
        if(u==null || !u.getMima().equals(password)) {
			return R.error("账号或密码不正确");
		}
		String token = tokenService.generateToken(u.getId(), u.getZhanghao(),"xuesheng",  "学生" );
		return R.ok().put("token", token);
	}

	/**
	 * 手机号+密码登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/loginByMobile")
	public R loginByMobile(String mobile, String password, HttpServletRequest request) {
        XueshengEntity u = xueshengService.getOne(new QueryWrapper<XueshengEntity>().eq("mobile", mobile));
        if(u==null) {
			return R.error("该手机号未注册");
		}
        if(!u.getMima().equals(password)) {
			return R.error("密码不正确");
		}
		String token = tokenService.generateToken(u.getId(), u.getZhanghao(),"xuesheng",  "学生" );
		return R.ok().put("token", token).put("data", u);
	}



	/**
     * 注册
     */
	@IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody XueshengEntity xuesheng, @RequestParam(required = false) String smscode){
    	XueshengEntity u = xueshengService.getOne(new QueryWrapper<XueshengEntity>().eq("mobile", xuesheng.getMobile()));
		if(u!=null) {
			return R.error("手机已被注册");
		}
        List<SmsregistercodeEntity> smsregistercodeList = smsregistercodeService.list(new QueryWrapper<SmsregistercodeEntity>().eq("role", "学生").eq("mobile", xuesheng.getMobile()).orderByDesc("addtime"));
        boolean smsValidate = false;
        if(smsregistercodeList!=null && smsregistercodeList.size()>0) {
            if(smsregistercodeList.get(0).getCode().equals(smscode)) {
                smsValidate = true;
            }
        }
        if(!smsValidate) return R.error("短信验证码不正确");
        
        String zhanghao = generateUniqueZhanghao();
        xuesheng.setZhanghao(zhanghao);
        
		Long uId = new Date().getTime();
		xuesheng.setId(uId);
        xuesheng.setMima(xuesheng.getMima());
        xueshengService.save(xuesheng);
        return R.ok().put("zhanghao", zhanghao);
    }
    
    /**
     * 生成唯一的4位数字账号
     */
    private String generateUniqueZhanghao() {
        String zhanghao;
        int maxRetries = 100;
        int retries = 0;
        do {
            zhanghao = String.format("%04d", (int)(Math.random() * 10000));
            retries++;
            if(retries > maxRetries) {
                throw new RuntimeException("生成账号失败，请稍后重试");
            }
        } while(xueshengService.count(new QueryWrapper<XueshengEntity>().eq("zhanghao", zhanghao)) > 0);
        return zhanghao;
    }


    /**
     * 发送短信
     */
    @IgnoreAuth
    @RequestMapping("/sendsms")
    public R sendsms(@RequestParam String mobile){
        String code = CommonUtil.getRandomNumber(6);
        SmsregistercodeEntity smsregistercode = new SmsregistercodeEntity();
        smsregistercode.setCode(code);
        smsregistercode.setRole("学生");
        smsregistercode.setMobile(mobile);
        smsregistercodeService.save(smsregistercode);
        CommonUtil.sendSMS(mobile, code);
        return R.ok().put("data", code);
    }

    /**
     * 发送登录短信
     */
    @IgnoreAuth
    @RequestMapping("/sendsms/login")
    public R sendsmsForLogin(@RequestParam String mobile){
        XueshengEntity u =xueshengService.getOne(new QueryWrapper<XueshengEntity>().eq("mobile", mobile));
        if(u==null) return R.error("用户不存在");
        String code = CommonUtil.getRandomNumber(6);
        SmsregistercodeEntity smsregistercode = new SmsregistercodeEntity();
        smsregistercode.setCode(code);
        smsregistercode.setRole("学生");
        smsregistercode.setMobile(mobile);
        smsregistercodeService.save(smsregistercode);
        CommonUtil.sendSMS(mobile, code);
        return R.ok().put("data", code);
    }

    /**
     * 手机号登录
     */
    @IgnoreAuth
    @RequestMapping("/sms/login")
    public R emailLogin(@RequestParam String mobile,@RequestParam(required = false) String smscode){
        XueshengEntity u =xueshengService.getOne(new QueryWrapper<XueshengEntity>().eq("mobile", mobile));
        if(u==null) return R.error("用户不存在");
        List<SmsregistercodeEntity> smsregistercodeList = smsregistercodeService.list(new QueryWrapper<SmsregistercodeEntity>().eq("role", "学生").eq("mobile", mobile).orderByDesc("addtime"));
        boolean smsValidate = false;
        if(smsregistercodeList!=null && smsregistercodeList.size()>0) {
            if(smsregistercodeList.get(0).getCode().equals(smscode)) {
                smsValidate = true;
            }
        }
        if(!smsValidate) return R.error("短信验证码不正确");
        String token = tokenService.generateToken(u.getId(), u.getZhanghao(),"xuesheng",  "学生" );
        return R.ok().put("token", token).put("username", u.getZhanghao());
    }


	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public R logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return R.ok("退出成功");
	}

	/**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        XueshengEntity u = xueshengService.getById(id);
        return R.ok().put("data", u);
    }

    /**
     * 密码重置
     */
    @IgnoreAuth
	@RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
        XueshengEntity u = xueshengService.getOne(new QueryWrapper<XueshengEntity>().eq("zhanghao", username));
    	if(u==null) {
    		return R.error("账号不存在");
    	}
        u.setMima("123456");
        xueshengService.updateById(u);
        return R.ok("密码已重置为：123456");
    }

    /**
     * 获取账号列表
     */
    @RequestMapping("/accountList")
    public R getAccountList(@RequestParam Map<String, Object> params,XueshengEntity xuesheng){
        QueryWrapper<XueshengEntity> ew = new QueryWrapper<XueshengEntity>();
        QueryWrapper<XueshengEntity> wrapper =MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xuesheng), params), params);
        List<Map> list = xueshengService.list(wrapper).stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", v.getId());
            map.put("account", v.getZhanghao());
            return map;
        }).collect(Collectors.toList());
        return R.ok().put("data", list);
    }






    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,XueshengEntity xuesheng,
		HttpServletRequest request){
        QueryWrapper<XueshengEntity> ew = new QueryWrapper<XueshengEntity>();


		PageUtils page = xueshengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xuesheng), params), params));
        Map<String, String> deSens = new HashMap<>();
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,XueshengEntity xuesheng,
		HttpServletRequest request){
        QueryWrapper<XueshengEntity> ew = new QueryWrapper<XueshengEntity>();

		PageUtils page = xueshengService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xuesheng), params), params));
        Map<String, String> deSens = new HashMap<>();
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( XueshengEntity xuesheng){
       	QueryWrapper<XueshengEntity> ew = new QueryWrapper<XueshengEntity>();
      	ew.allEq(MPUtil.allEQMapPre( xuesheng, "xuesheng"));
        return R.ok().put("data", xueshengService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(XueshengEntity xuesheng){
        QueryWrapper< XueshengEntity> ew = new QueryWrapper< XueshengEntity>();
 		ew.allEq(MPUtil.allEQMapPre( xuesheng, "xuesheng"));
		XueshengView xueshengView =  xueshengService.selectView(ew);
		return R.ok("查询学生成功").put("data", xueshengView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        XueshengEntity xuesheng = xueshengService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        DeSensUtil.desensitize(xuesheng,deSens);
        return R.ok().put("data", xuesheng);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        XueshengEntity xuesheng = xueshengService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        DeSensUtil.desensitize(xuesheng,deSens);
        return R.ok().put("data", xuesheng);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody XueshengEntity xuesheng, HttpServletRequest request){
        String zhanghao = generateUniqueZhanghao();
        xuesheng.setZhanghao(zhanghao);
    	xuesheng.setId(new Date().getTime());
        xueshengService.save(xuesheng);
        return R.ok().put("data",xuesheng.getId());
    }

    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody XueshengEntity xuesheng, HttpServletRequest request){
        String zhanghao = generateUniqueZhanghao();
        xuesheng.setZhanghao(zhanghao);
    	xuesheng.setId(new Date().getTime());
        xueshengService.save(xuesheng);
        return R.ok().put("data",xuesheng.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        XueshengEntity xuesheng = xueshengService.getOne(new QueryWrapper<XueshengEntity>().eq("zhanghao", username));
        return R.ok().put("data", xuesheng);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody XueshengEntity xuesheng, HttpServletRequest request){
	    XueshengEntity xueshengEntity = xueshengService.getById(xuesheng.getId());
        if(StringUtils.isNotBlank(xuesheng.getMima()) && !xuesheng.getMima().equals(xueshengEntity.getMima())) {
            xuesheng.setMima(xuesheng.getMima());
        }
        xuesheng.setZhanghao(xueshengEntity.getZhanghao());
        xueshengService.updateById(xuesheng);
        if(null!=xuesheng.getZhanghao())
        {
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setUsername(xuesheng.getZhanghao());
            tokenService.update(tokenEntity, new UpdateWrapper<TokenEntity>().eq("userid", xuesheng.getId()));
        }
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        xueshengService.removeBatchByIds(Arrays.asList(ids));
        return R.ok();
    }





    // hasAlipay:否




}
