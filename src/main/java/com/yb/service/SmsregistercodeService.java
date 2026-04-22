package com.yb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.utils.PageUtils;
import com.yb.entity.SmsregistercodeEntity;
import java.util.List;
import java.util.Map;
import com.yb.entity.vo.SmsregistercodeVO;
import org.apache.ibatis.annotations.Param;
import com.yb.entity.view.SmsregistercodeView;


/**
 * 短信验证码
 *
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
public interface SmsregistercodeService extends IService<SmsregistercodeEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<SmsregistercodeVO> selectListVO(QueryWrapper<SmsregistercodeEntity> wrapper);
   	
   	SmsregistercodeVO selectVO(@Param("ew") QueryWrapper<SmsregistercodeEntity> wrapper);
   	
   	List<SmsregistercodeView> selectListView(QueryWrapper<SmsregistercodeEntity> wrapper);
   	
   	SmsregistercodeView selectView(@Param("ew") QueryWrapper<SmsregistercodeEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,QueryWrapper<SmsregistercodeEntity> wrapper);

   	

}

