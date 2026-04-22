package com.yb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.utils.PageUtils;
import com.yb.entity.ExamquestionEntity;
import java.util.List;
import java.util.Map;
import com.yb.entity.vo.ExamquestionVO;
import org.apache.ibatis.annotations.Param;
import com.yb.entity.view.ExamquestionView;


/**
 * 试题
 *
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public interface ExamquestionService extends IService<ExamquestionEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ExamquestionVO> selectListVO(QueryWrapper<ExamquestionEntity> wrapper);
   	
   	ExamquestionVO selectVO(@Param("ew") QueryWrapper<ExamquestionEntity> wrapper);
   	
   	List<ExamquestionView> selectListView(QueryWrapper<ExamquestionEntity> wrapper);
   	
   	ExamquestionView selectView(@Param("ew") QueryWrapper<ExamquestionEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,QueryWrapper<ExamquestionEntity> wrapper);

   	

}

