package com.yb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.utils.PageUtils;
import com.yb.entity.ForumEntity;
import java.util.List;
import java.util.Map;
import com.yb.entity.vo.ForumVO;
import org.apache.ibatis.annotations.Param;
import com.yb.entity.view.ForumView;


/**
 * 互动学习
 *
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public interface ForumService extends IService<ForumEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<ForumVO> selectListVO(QueryWrapper<ForumEntity> wrapper);
   	
   	ForumVO selectVO(@Param("ew") QueryWrapper<ForumEntity> wrapper);
   	
   	List<ForumView> selectListView(QueryWrapper<ForumEntity> wrapper);
   	
   	ForumView selectView(@Param("ew") QueryWrapper<ForumEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,QueryWrapper<ForumEntity> wrapper);

   	

}

