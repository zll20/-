package com.yb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.utils.PageUtils;
import com.yb.entity.DancikuEntity;
import java.util.List;
import java.util.Map;
import com.yb.entity.vo.DancikuVO;
import org.apache.ibatis.annotations.Param;
import com.yb.entity.view.DancikuView;


/**
 * 单词库
 *
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public interface DancikuService extends IService<DancikuEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DancikuVO> selectListVO(QueryWrapper<DancikuEntity> wrapper);
   	
   	DancikuVO selectVO(@Param("ew") QueryWrapper<DancikuEntity> wrapper);
   	
   	List<DancikuView> selectListView(QueryWrapper<DancikuEntity> wrapper);
   	
   	DancikuView selectView(@Param("ew") QueryWrapper<DancikuEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,QueryWrapper<DancikuEntity> wrapper);

   	

}

