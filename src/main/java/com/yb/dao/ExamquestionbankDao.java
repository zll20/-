package com.yb.dao;

import com.yb.entity.ExamquestionbankEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import com.yb.entity.vo.ExamquestionbankVO;
import com.yb.entity.view.ExamquestionbankView;


/**
 * 试题库
 * 
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
public interface ExamquestionbankDao extends BaseMapper<ExamquestionbankEntity> {
	
	List<ExamquestionbankVO> selectListVO(@Param("ew") QueryWrapper<ExamquestionbankEntity> wrapper);
	
	ExamquestionbankVO selectVO(@Param("ew") QueryWrapper<ExamquestionbankEntity> wrapper);
	
	List<ExamquestionbankView> selectListView(@Param("ew") QueryWrapper<ExamquestionbankEntity> wrapper);

	List<ExamquestionbankView> selectListView(Page page,@Param("ew") QueryWrapper<ExamquestionbankEntity> wrapper);

	
	ExamquestionbankView selectView(@Param("ew") QueryWrapper<ExamquestionbankEntity> wrapper);
	

}
