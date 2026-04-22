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
import com.yb.utils.UserBasedCollaborativeFiltering;
import com.yb.algorithm.recommend.RecommendAlgorithmFactory;

import com.yb.entity.DancikuEntity;
import com.yb.entity.view.DancikuView;

import com.yb.service.DancikuService;
import com.yb.utils.PageUtils;
import com.yb.utils.R;
import com.yb.utils.EncryptUtil;
import com.yb.utils.MPUtil;
import com.yb.utils.MapUtils;
import com.yb.utils.CommonUtil;
import java.io.IOException;
import com.yb.service.StoreupService;
import com.yb.entity.StoreupEntity;

/**
 * 单词库
 * 后端接口
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
@RestController
@RequestMapping("/danciku")
public class DancikuController {
    @Autowired
    private DancikuService dancikuService;

    @Autowired
    private StoreupService storeupService;









    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,DancikuEntity danciku,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<DancikuEntity> ew = new QueryWrapper<DancikuEntity>();


        //查询结果
		PageUtils page = dancikuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, danciku), params), params));
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
    public R list(@RequestParam Map<String, Object> params,DancikuEntity danciku,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date clicktimestart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date clicktimeend,
                @RequestParam(required = false) Double discussnumstart,
                @RequestParam(required = false) Double discussnumend,
                @RequestParam(required = false) Double storeupnumstart,
                @RequestParam(required = false) Double storeupnumend,
		HttpServletRequest request){
        //设置查询条件
        QueryWrapper<DancikuEntity> ew = new QueryWrapper<DancikuEntity>();
        if(clicktimestart!=null) ew.ge("clicktime", clicktimestart);
        if(clicktimeend!=null) ew.le("clicktime", clicktimeend);
        if(discussnumstart!=null) ew.ge("discussnum", discussnumstart);
        if(discussnumend!=null) ew.le("discussnum", discussnumend);
        if(storeupnumstart!=null) ew.ge("storeupnum", storeupnumstart);
        if(storeupnumend!=null) ew.le("storeupnum", storeupnumend);

        //查询结果
		PageUtils page = dancikuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, danciku), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DancikuEntity danciku){
       	QueryWrapper<DancikuEntity> ew = new QueryWrapper<DancikuEntity>();
      	ew.allEq(MPUtil.allEQMapPre( danciku, "danciku"));
        return R.ok().put("data", dancikuService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DancikuEntity danciku){
        QueryWrapper< DancikuEntity> ew = new QueryWrapper< DancikuEntity>();
 		ew.allEq(MPUtil.allEQMapPre( danciku, "danciku"));
		DancikuView dancikuView =  dancikuService.selectView(ew);
		return R.ok("查询单词库成功").put("data", dancikuView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DancikuEntity danciku = dancikuService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(danciku,deSens);
        return R.ok().put("data", danciku);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DancikuEntity danciku = dancikuService.getById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(danciku,deSens);
        return R.ok().put("data", danciku);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DancikuEntity danciku, HttpServletRequest request){
        //ValidatorUtils.validateEntity(danciku);
        dancikuService.save(danciku);
        return R.ok().put("data",danciku.getId());
    }

    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody DancikuEntity danciku, HttpServletRequest request){
        //ValidatorUtils.validateEntity(danciku);
        dancikuService.save(danciku);
        return R.ok().put("data",danciku.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody DancikuEntity danciku, HttpServletRequest request){
        //ValidatorUtils.validateEntity(danciku);
        //全部更新
        dancikuService.updateById(danciku);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        dancikuService.removeBatchByIds(Arrays.asList(ids));
        return R.ok();
    }

	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,DancikuEntity danciku, HttpServletRequest request,String pre){
        QueryWrapper<DancikuEntity> ew = new QueryWrapper<DancikuEntity>();
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

		PageUtils page = dancikuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, danciku), params), params));
        return R.ok().put("data", page);
    }


    /**
     * 协同算法（基于用户的协同算法：购买、收藏、关注）
     */
    @RequestMapping("/autoSort2")
    public R autoSort2(@RequestParam Map<String, Object> params,DancikuEntity danciku, HttpServletRequest request){
        String userId = request.getSession().getAttribute("userId").toString();
        Integer limit = params.get("limit") == null ? 10 : Integer.parseInt(params.get("limit").toString());
        List<RecommendAlgorithmFactory.UserBehavior> userBehaviors = new ArrayList<>();
        userBehaviors.addAll(storeupService.list(new QueryWrapper<StoreupEntity>().eq("type", 1 ).eq("tablename", "danciku")).stream().map(storeup -> {
            return new RecommendAlgorithmFactory.UserBehavior(storeup.getUserid(), storeup.getRefid());
        }).collect(Collectors.toList()));
        List<Long> recommendations = RecommendAlgorithmFactory.buildUserItemMatrix(userBehaviors).recommendItems(Long.parseLong(userId), limit);
        System.out.println("Recommendations for " + userId + ":");
        recommendations.forEach(recommendation -> System.out.println(recommendation));
        QueryWrapper<DancikuEntity> ew = new QueryWrapper<DancikuEntity>();

        if(recommendations!=null && recommendations.size()>0) {
            ew.in("id", recommendations);
            ew.last("order by FIELD(id, "+String.join(",", recommendations.stream().map(String::valueOf).collect(Collectors.toList()))+")");
        }
        PageUtils page = dancikuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, danciku), params), params));
        List<DancikuEntity> pageList = (List<DancikuEntity>)page.getList();
        if(recommendations!=null && recommendations.size()>0 && pageList.size()<limit) {
            int toAddNum = limit-pageList.size();
            ew = new QueryWrapper<DancikuEntity>();
            ew.notIn("id", recommendations);
            ew.orderByAsc("id");
            ew.last("limit "+toAddNum);
            pageList.addAll(dancikuService.list(ew));
        } else if(pageList.size()>limit) {
            pageList = pageList.subList(0, limit);
        }
        page.setList(pageList);
        return R.ok().put("data", page);
    }


    /**
     * 获取当前单词的上一个单词（同一级别）
     */
    @IgnoreAuth
    @RequestMapping("/prev/{id}")
    public R getPrevWord(@PathVariable("id") Long id, 
                         @RequestParam(required = false) String jibie) {
        QueryWrapper<DancikuEntity> ew = new QueryWrapper<>();
        
        if (StringUtils.isNotBlank(jibie)) {
            ew.eq("jibie", jibie);
        }
        
        ew.lt("id", id);
        ew.orderByDesc("id");
        ew.last("LIMIT 1");
        
        DancikuEntity prevWord = dancikuService.getOne(ew);
        
        if (prevWord != null) {
            Map<String, String> deSens = new HashMap<>();
            DeSensUtil.desensitize(prevWord, deSens);
            return R.ok().put("data", prevWord);
        } else {
            return R.error("已经是第一个单词了");
        }
    }

    /**
     * 获取当前单词的下一个单词（同一级别）
     */
    @IgnoreAuth
    @RequestMapping("/next/{id}")
    public R getNextWord(@PathVariable("id") Long id,
                         @RequestParam(required = false) String jibie) {
        QueryWrapper<DancikuEntity> ew = new QueryWrapper<>();
        
        if (StringUtils.isNotBlank(jibie)) {
            ew.eq("jibie", jibie);
        }
        
        ew.gt("id", id);
        ew.orderByAsc("id");
        ew.last("LIMIT 1");
        
        DancikuEntity nextWord = dancikuService.getOne(ew);
        
        if (nextWord != null) {
            Map<String, String> deSens = new HashMap<>();
            DeSensUtil.desensitize(nextWord, deSens);
            return R.ok().put("data", nextWord);
        } else {
            return R.error("已经是最后一个单词了");
        }
    }


    // hasAlipay:否




}
