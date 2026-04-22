package com.yb.dao;

import com.yb.entity.ExamquestionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import com.yb.entity.vo.ExamquestionVO;
import com.yb.entity.view.ExamquestionView;


/**
 * 试题
 * 
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public interface ExamquestionDao extends BaseMapper<ExamquestionEntity> {
	
	List<ExamquestionVO> selectListVO(@Param("ew") QueryWrapper<ExamquestionEntity> wrapper);
	
	ExamquestionVO selectVO(@Param("ew") QueryWrapper<ExamquestionEntity> wrapper);
	
	List<ExamquestionView> selectListView(@Param("ew") QueryWrapper<ExamquestionEntity> wrapper);

	List<ExamquestionView> selectListView(Page page,@Param("ew") QueryWrapper<ExamquestionEntity> wrapper);

	
	ExamquestionView selectView(@Param("ew") QueryWrapper<ExamquestionEntity> wrapper);
	

}
