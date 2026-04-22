package com.yb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.utils.PageUtils;
import com.yb.entity.DancifenleiEntity;
import java.util.List;
import java.util.Map;
import com.yb.entity.vo.DancifenleiVO;
import org.apache.ibatis.annotations.Param;
import com.yb.entity.view.DancifenleiView;


/**
 * 单词分类
 *
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public interface DancifenleiService extends IService<DancifenleiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DancifenleiVO> selectListVO(QueryWrapper<DancifenleiEntity> wrapper);
   	
   	DancifenleiVO selectVO(@Param("ew") QueryWrapper<DancifenleiEntity> wrapper);
   	
   	List<DancifenleiView> selectListView(QueryWrapper<DancifenleiEntity> wrapper);
   	
   	DancifenleiView selectView(@Param("ew") QueryWrapper<DancifenleiEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,QueryWrapper<DancifenleiEntity> wrapper);

   	

}

