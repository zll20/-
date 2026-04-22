package com.yb.dao;

import com.yb.entity.DancikuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import com.yb.entity.vo.DancikuVO;
import com.yb.entity.view.DancikuView;


/**
 * 单词库
 * 
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public interface DancikuDao extends BaseMapper<DancikuEntity> {
	
	List<DancikuVO> selectListVO(@Param("ew") QueryWrapper<DancikuEntity> wrapper);
	
	DancikuVO selectVO(@Param("ew") QueryWrapper<DancikuEntity> wrapper);
	
	List<DancikuView> selectListView(@Param("ew") QueryWrapper<DancikuEntity> wrapper);

	List<DancikuView> selectListView(Page page,@Param("ew") QueryWrapper<DancikuEntity> wrapper);

	
	DancikuView selectView(@Param("ew") QueryWrapper<DancikuEntity> wrapper);
	

}
