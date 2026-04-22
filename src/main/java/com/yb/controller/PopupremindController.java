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

import com.yb.entity.PopupremindEntity;
import com.yb.entity.view.PopupremindView;

import com.yb.service.PopupremindService;
import com.yb.utils.PageUtils;
import com.yb.utils.R;
import com.yb.utils.EncryptUtil;
import com.yb.utils.MPUtil;
import com.yb.utils.MapUtils;
import com.yb.utils.CommonUtil;
import java.io.IOException;

/**
 * 弹窗提醒
 * 后端接口
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
@RestController
@RequestMapping("/popupremind")
public class PopupremindController {
    @Autowired
    private PopupremindService popupremindService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,PopupremindEntity popupremind,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date remindtimestart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date remindtimeend,
		HttpServletRequest request){
        if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
            popupremind.setUserid((Long)request.getSession().getAttribute("userId"));
        }
        //设置查询条件
        QueryWrapper<PopupremindEntity> ew = new QueryWrapper<PopupremindEntity>();
        if(remindtimestart!=null) ew.ge("remindtime", remindtimestart);
        if(remindtimeend!=null) ew.le("remindtime", remindtimeend);


        //查询结果
		PageUtils page = popupremindService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, popupremind), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }

    @RequestMapping("/message/list")
    public R messageList(@RequestParam Map<String, Object> params,PopupremindEntity popupremind,HttpServletRequest request){
        //设置查询条件
        QueryWrapper<PopupremindEntity> ew = new QueryWrapper<PopupremindEntity>();
        QueryWrapper<PopupremindEntity> wrapper = MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, popupremind), params), params);
        wrapper.and(w->{
            w.eq("type", "全局");
            if (!request.getSession().getAttribute("role").toString().equals("管理员")) {
                w.or().eq("userid", (Long) request.getSession().getAttribute("userId"));
            }
        });
        return R.ok().put("data", popupremindService.queryPage(params, wrapper));
    }

    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,PopupremindEntity popupremind,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date remindtimestart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date remindtimeend,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<PopupremindEntity> ew = new QueryWrapper<PopupremindEntity>();
        if(remindtimestart!=null) ew.ge("remindtime", remindtimestart);
        if(remindtimeend!=null) ew.le("remindtime", remindtimeend);

        //查询结果
		PageUtils page = popupremindService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, popupremind), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( PopupremindEntity popupremind){
       	QueryWrapper<PopupremindEntity> ew = new QueryWrapper<PopupremindEntity>();
      	ew.allEq(MPUtil.allEQMapPre( popupremind, "popupremind"));
        return R.ok().put("data", popupremindService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(PopupremindEntity popupremind){
        QueryWrapper< PopupremindEntity> ew = new QueryWrapper< PopupremindEntity>();
 		ew.allEq(MPUtil.allEQMapPre( popupremind, "popupremind"));
		PopupremindView popupremindView =  popupremindService.selectView(ew);
		return R.ok("查询弹窗提醒成功").put("data", popupremindView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        PopupremindEntity popupremind = popupremindService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(popupremind,deSens);
        return R.ok().put("data", popupremind);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        PopupremindEntity popupremind = popupremindService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(popupremind,deSens);
        return R.ok().put("data", popupremind);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PopupremindEntity popupremind, HttpServletRequest request){
        //ValidatorUtils.validateEntity(popupremind);
        if(popupremind.getUserid()==null){
            popupremind.setUserid((Long) request.getSession().getAttribute("userId"));
        }
        popupremindService.save(popupremind);
        return R.ok().put("data",popupremind.getId());
    }

    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody PopupremindEntity popupremind, HttpServletRequest request){
        //ValidatorUtils.validateEntity(popupremind);
        popupremindService.save(popupremind);
        return R.ok().put("data",popupremind.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        PopupremindEntity popupremind = popupremindService.getOne(new QueryWrapper<PopupremindEntity>().eq("", username));
        return R.ok().put("data", popupremind);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody PopupremindEntity popupremind, HttpServletRequest request){
        //ValidatorUtils.validateEntity(popupremind);
        //全部更新
        popupremindService.updateById(popupremind);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        popupremindService.removeBatchByIds(Arrays.asList(ids));
        return R.ok();
    }

	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,PopupremindEntity popupremind, HttpServletRequest request,String pre){
        QueryWrapper<PopupremindEntity> ew = new QueryWrapper<PopupremindEntity>();
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

		PageUtils page = popupremindService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, popupremind), params), params));
        return R.ok().put("data", page);
    }




    // hasAlipay:$hasAlipay




}
