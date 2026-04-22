package com.yb.dao;

import com.yb.entity.DiscussdancikuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import com.yb.entity.vo.DiscussdancikuVO;
import com.yb.entity.view.DiscussdancikuView;


/**
 * 单词库评论
 * 
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
public interface DiscussdancikuDao extends BaseMapper<DiscussdancikuEntity> {
	
	List<DiscussdancikuVO> selectListVO(@Param("ew") QueryWrapper<DiscussdancikuEntity> wrapper);
	
	DiscussdancikuVO selectVO(@Param("ew") QueryWrapper<DiscussdancikuEntity> wrapper);
	
	List<DiscussdancikuView> selectListView(@Param("ew") QueryWrapper<DiscussdancikuEntity> wrapper);

	List<DiscussdancikuView> selectListView(Page page,@Param("ew") QueryWrapper<DiscussdancikuEntity> wrapper);

	
	DiscussdancikuView selectView(@Param("ew") QueryWrapper<DiscussdancikuEntity> wrapper);
	

}
