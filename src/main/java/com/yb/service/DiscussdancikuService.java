package com.yb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.utils.PageUtils;
import com.yb.entity.DiscussdancikuEntity;
import java.util.List;
import java.util.Map;
import com.yb.entity.vo.DiscussdancikuVO;
import org.apache.ibatis.annotations.Param;
import com.yb.entity.view.DiscussdancikuView;


/**
 * 单词库评论
 *
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
public interface DiscussdancikuService extends IService<DiscussdancikuEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DiscussdancikuVO> selectListVO(QueryWrapper<DiscussdancikuEntity> wrapper);
   	
   	DiscussdancikuVO selectVO(@Param("ew") QueryWrapper<DiscussdancikuEntity> wrapper);
   	
   	List<DiscussdancikuView> selectListView(QueryWrapper<DiscussdancikuEntity> wrapper);
   	
   	DiscussdancikuView selectView(@Param("ew") QueryWrapper<DiscussdancikuEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,QueryWrapper<DiscussdancikuEntity> wrapper);

   	

}

