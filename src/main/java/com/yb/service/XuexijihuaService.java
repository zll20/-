package com.yb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.utils.PageUtils;
import com.yb.entity.XuexijihuaEntity;
import java.util.List;
import java.util.Map;
import com.yb.entity.vo.XuexijihuaVO;
import org.apache.ibatis.annotations.Param;
import com.yb.entity.view.XuexijihuaView;


/**
 * 学习计划
 *
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public interface XuexijihuaService extends IService<XuexijihuaEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<XuexijihuaVO> selectListVO(QueryWrapper<XuexijihuaEntity> wrapper);
   	
   	XuexijihuaVO selectVO(@Param("ew") QueryWrapper<XuexijihuaEntity> wrapper);
   	
   	List<XuexijihuaView> selectListView(QueryWrapper<XuexijihuaEntity> wrapper);
   	
   	XuexijihuaView selectView(@Param("ew") QueryWrapper<XuexijihuaEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,QueryWrapper<XuexijihuaEntity> wrapper);

   	

}

