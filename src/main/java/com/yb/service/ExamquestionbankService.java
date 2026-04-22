package com.yb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.utils.PageUtils;
import com.yb.entity.ExamquestionbankEntity;
import java.util.List;
import java.util.Map;
import com.yb.entity.vo.ExamquestionbankVO;
import org.apache.ibatis.annotations.Param;
import com.yb.entity.view.ExamquestionbankView;


/**
 * 试题库
 *
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
public interface ExamquestionbankService extends IService<ExamquestionbankEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ExamquestionbankVO> selectListVO(QueryWrapper<ExamquestionbankEntity> wrapper);
   	
   	ExamquestionbankVO selectVO(@Param("ew") QueryWrapper<ExamquestionbankEntity> wrapper);
   	
   	List<ExamquestionbankView> selectListView(QueryWrapper<ExamquestionbankEntity> wrapper);
   	
   	ExamquestionbankView selectView(@Param("ew") QueryWrapper<ExamquestionbankEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,QueryWrapper<ExamquestionbankEntity> wrapper);

   	

}

