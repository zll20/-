package com.yb.dao;

import com.yb.entity.SmsregistercodeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import com.yb.entity.vo.SmsregistercodeVO;
import com.yb.entity.view.SmsregistercodeView;


/**
 * 短信验证码
 * 
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
public interface SmsregistercodeDao extends BaseMapper<SmsregistercodeEntity> {
	
	List<SmsregistercodeVO> selectListVO(@Param("ew") QueryWrapper<SmsregistercodeEntity> wrapper);
	
	SmsregistercodeVO selectVO(@Param("ew") QueryWrapper<SmsregistercodeEntity> wrapper);
	
	List<SmsregistercodeView> selectListView(@Param("ew") QueryWrapper<SmsregistercodeEntity> wrapper);

	List<SmsregistercodeView> selectListView(Page page,@Param("ew") QueryWrapper<SmsregistercodeEntity> wrapper);

	
	SmsregistercodeView selectView(@Param("ew") QueryWrapper<SmsregistercodeEntity> wrapper);
	

}
