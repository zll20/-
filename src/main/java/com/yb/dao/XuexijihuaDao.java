package com.yb.dao;

import com.yb.entity.XuexijihuaEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import com.yb.entity.vo.XuexijihuaVO;
import com.yb.entity.view.XuexijihuaView;


/**
 * 学习计划
 * 
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public interface XuexijihuaDao extends BaseMapper<XuexijihuaEntity> {
	
	List<XuexijihuaVO> selectListVO(@Param("ew") QueryWrapper<XuexijihuaEntity> wrapper);
	
	XuexijihuaVO selectVO(@Param("ew") QueryWrapper<XuexijihuaEntity> wrapper);
	
	List<XuexijihuaView> selectListView(@Param("ew") QueryWrapper<XuexijihuaEntity> wrapper);

	List<XuexijihuaView> selectListView(Page page,@Param("ew") QueryWrapper<XuexijihuaEntity> wrapper);

	
	XuexijihuaView selectView(@Param("ew") QueryWrapper<XuexijihuaEntity> wrapper);
	

}
