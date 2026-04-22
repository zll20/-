package com.yb.dao;

import com.yb.entity.PopupremindEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import com.yb.entity.vo.PopupremindVO;
import com.yb.entity.view.PopupremindView;


/**
 * 弹窗提醒
 * 
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
public interface PopupremindDao extends BaseMapper<PopupremindEntity> {
	
	List<PopupremindVO> selectListVO(@Param("ew") QueryWrapper<PopupremindEntity> wrapper);
	
	PopupremindVO selectVO(@Param("ew") QueryWrapper<PopupremindEntity> wrapper);
	
	List<PopupremindView> selectListView(@Param("ew") QueryWrapper<PopupremindEntity> wrapper);

	List<PopupremindView> selectListView(Page page,@Param("ew") QueryWrapper<PopupremindEntity> wrapper);

	
	PopupremindView selectView(@Param("ew") QueryWrapper<PopupremindEntity> wrapper);
	

}
