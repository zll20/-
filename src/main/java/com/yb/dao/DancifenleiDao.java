package com.yb.dao;

import com.yb.entity.DancifenleiEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import com.yb.entity.vo.DancifenleiVO;
import com.yb.entity.view.DancifenleiView;


/**
 * 单词分类
 * 
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public interface DancifenleiDao extends BaseMapper<DancifenleiEntity> {
	
	List<DancifenleiVO> selectListVO(@Param("ew") QueryWrapper<DancifenleiEntity> wrapper);
	
	DancifenleiVO selectVO(@Param("ew") QueryWrapper<DancifenleiEntity> wrapper);
	
	List<DancifenleiView> selectListView(@Param("ew") QueryWrapper<DancifenleiEntity> wrapper);

	List<DancifenleiView> selectListView(Page page,@Param("ew") QueryWrapper<DancifenleiEntity> wrapper);

	
	DancifenleiView selectView(@Param("ew") QueryWrapper<DancifenleiEntity> wrapper);
	

}
