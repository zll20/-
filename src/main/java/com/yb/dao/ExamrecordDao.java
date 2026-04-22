package com.yb.dao;

import com.yb.entity.ExamrecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import com.yb.entity.vo.ExamrecordVO;
import com.yb.entity.view.ExamrecordView;


/**
 * 考试记录
 * 
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
public interface ExamrecordDao extends BaseMapper<ExamrecordEntity> {
	
	List<ExamrecordVO> selectListVO(@Param("ew") QueryWrapper<ExamrecordEntity> wrapper);
	
	ExamrecordVO selectVO(@Param("ew") QueryWrapper<ExamrecordEntity> wrapper);
	
	List<ExamrecordView> selectListView(@Param("ew") QueryWrapper<ExamrecordEntity> wrapper);

	List<ExamrecordView> selectListView(Page page,@Param("ew") QueryWrapper<ExamrecordEntity> wrapper);

	
	ExamrecordView selectView(@Param("ew") QueryWrapper<ExamrecordEntity> wrapper);
	
	List<ExamrecordView> selectGroupBy(Page page,@Param("ew") QueryWrapper<ExamrecordEntity> wrapper);

    List<ExamrecordView> selectOptionsNum(Page page,@Param("ew") QueryWrapper<ExamrecordEntity> wrapper);

}
