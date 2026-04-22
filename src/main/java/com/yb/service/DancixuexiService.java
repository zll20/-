package com.yb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.utils.PageUtils;
import com.yb.entity.DancixuexiEntity;
import java.util.List;
import java.util.Map;
import com.yb.entity.vo.DancixuexiVO;
import org.apache.ibatis.annotations.Param;
import com.yb.entity.view.DancixuexiView;


/**
 * 单词学习
 *
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public interface DancixuexiService extends IService<DancixuexiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DancixuexiVO> selectListVO(QueryWrapper<DancixuexiEntity> wrapper);
   	
   	DancixuexiVO selectVO(@Param("ew") QueryWrapper<DancixuexiEntity> wrapper);
   	
   	List<DancixuexiView> selectListView(QueryWrapper<DancixuexiEntity> wrapper);
   	
   	DancixuexiView selectView(@Param("ew") QueryWrapper<DancixuexiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,QueryWrapper<DancixuexiEntity> wrapper);

   	

    List<Map<String, Object>> selectValue(Map<String, Object> params,QueryWrapper<DancixuexiEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params,QueryWrapper<DancixuexiEntity> wrapper);

    List<Map<String, Object>> selectGroup(Map<String, Object> params,QueryWrapper<DancixuexiEntity> wrapper);



}

