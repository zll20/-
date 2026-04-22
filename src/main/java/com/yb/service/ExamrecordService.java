package com.yb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.utils.PageUtils;
import com.yb.entity.ExamrecordEntity;
import java.util.List;
import java.util.Map;
import com.yb.entity.vo.ExamrecordVO;
import org.apache.ibatis.annotations.Param;
import com.yb.entity.view.ExamrecordView;


/**
 * 考试记录
 *
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
public interface ExamrecordService extends IService<ExamrecordEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ExamrecordVO> selectListVO(QueryWrapper<ExamrecordEntity> wrapper);
   	
   	ExamrecordVO selectVO(@Param("ew") QueryWrapper<ExamrecordEntity> wrapper);
   	
   	List<ExamrecordView> selectListView(QueryWrapper<ExamrecordEntity> wrapper);
   	
   	ExamrecordView selectView(@Param("ew") QueryWrapper<ExamrecordEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,QueryWrapper<ExamrecordEntity> wrapper);

   	
   	PageUtils queryPageGroupBy(Map<String, Object> params,QueryWrapper<ExamrecordEntity> wrapper);

    PageUtils queryPageOptionsNum(Map<String, Object> params,QueryWrapper<ExamrecordEntity> wrapper);

}

