package com.yb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.utils.PageUtils;
import com.yb.entity.PopupremindEntity;
import java.util.List;
import java.util.Map;
import com.yb.entity.vo.PopupremindVO;
import org.apache.ibatis.annotations.Param;
import com.yb.entity.view.PopupremindView;


/**
 * 弹窗提醒
 *
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
public interface PopupremindService extends IService<PopupremindEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<PopupremindVO> selectListVO(QueryWrapper<PopupremindEntity> wrapper);
   	
   	PopupremindVO selectVO(@Param("ew") QueryWrapper<PopupremindEntity> wrapper);
   	
   	List<PopupremindView> selectListView(QueryWrapper<PopupremindEntity> wrapper);
   	
   	PopupremindView selectView(@Param("ew") QueryWrapper<PopupremindEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,QueryWrapper<PopupremindEntity> wrapper);

   	

}

