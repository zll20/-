package com.yb.dao;

import com.yb.entity.XueshengEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import com.yb.entity.vo.XueshengVO;
import com.yb.entity.view.XueshengView;


/**
 * 学生
 * 
 * @author 
 * @email 
 * @date 2026-03-14 23:57:23
 */
public interface XueshengDao extends BaseMapper<XueshengEntity> {
	
	List<XueshengVO> selectListVO(@Param("ew") QueryWrapper<XueshengEntity> wrapper);
	
	XueshengVO selectVO(@Param("ew") QueryWrapper<XueshengEntity> wrapper);
	
	List<XueshengView> selectListView(@Param("ew") QueryWrapper<XueshengEntity> wrapper);

	List<XueshengView> selectListView(Page page,@Param("ew") QueryWrapper<XueshengEntity> wrapper);

	
	XueshengView selectView(@Param("ew") QueryWrapper<XueshengEntity> wrapper);
	

}
