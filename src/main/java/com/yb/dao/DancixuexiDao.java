package com.yb.dao;

import com.yb.entity.DancixuexiEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import com.yb.entity.vo.DancixuexiVO;
import com.yb.entity.view.DancixuexiView;


/**
 * 单词学习
 * 
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public interface DancixuexiDao extends BaseMapper<DancixuexiEntity> {
	
	List<DancixuexiVO> selectListVO(@Param("ew") QueryWrapper<DancixuexiEntity> wrapper);
	
	DancixuexiVO selectVO(@Param("ew") QueryWrapper<DancixuexiEntity> wrapper);
	
	List<DancixuexiView> selectListView(@Param("ew") QueryWrapper<DancixuexiEntity> wrapper);

	List<DancixuexiView> selectListView(Page page,@Param("ew") QueryWrapper<DancixuexiEntity> wrapper);

	
	DancixuexiView selectView(@Param("ew") QueryWrapper<DancixuexiEntity> wrapper);
	

    List<Map<String, Object>> selectValue(@Param("params") Map<String, Object> params,@Param("ew") QueryWrapper<DancixuexiEntity> wrapper);

    List<Map<String, Object>> selectTimeStatValue(@Param("params") Map<String, Object> params,@Param("ew") QueryWrapper<DancixuexiEntity> wrapper);

    List<Map<String, Object>> selectGroup(@Param("params") Map<String, Object> params,@Param("ew") QueryWrapper<DancixuexiEntity> wrapper);



}
