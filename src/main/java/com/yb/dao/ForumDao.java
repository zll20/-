package com.yb.dao;

import com.yb.entity.ForumEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import com.yb.entity.vo.ForumVO;
import com.yb.entity.view.ForumView;


/**
 * 互动学习
 * 
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public interface ForumDao extends BaseMapper<ForumEntity> {
	
	List<ForumVO> selectListVO(@Param("ew") QueryWrapper<ForumEntity> wrapper);
	
	ForumVO selectVO(@Param("ew") QueryWrapper<ForumEntity> wrapper);
	
	List<ForumView> selectListView(@Param("ew") QueryWrapper<ForumEntity> wrapper);

	List<ForumView> selectListView(Page page,@Param("ew") QueryWrapper<ForumEntity> wrapper);

	
	ForumView selectView(@Param("ew") QueryWrapper<ForumEntity> wrapper);
	

}
