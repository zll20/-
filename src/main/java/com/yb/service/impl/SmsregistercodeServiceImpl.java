package com.yb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;


import com.yb.dao.SmsregistercodeDao;
import com.yb.entity.SmsregistercodeEntity;
import com.yb.service.SmsregistercodeService;
import com.yb.entity.vo.SmsregistercodeVO;
import com.yb.entity.view.SmsregistercodeView;

@Service("smsregistercodeService")
public class SmsregistercodeServiceImpl extends ServiceImpl<SmsregistercodeDao, SmsregistercodeEntity> implements SmsregistercodeService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SmsregistercodeEntity> page = baseMapper.selectPage(new Query<SmsregistercodeEntity>(params).getPage(),new QueryWrapper<SmsregistercodeEntity>());
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, QueryWrapper<SmsregistercodeEntity> wrapper) {
        Page<SmsregistercodeEntity> page = baseMapper.selectPage(new Query<SmsregistercodeEntity>(params).getPage(),wrapper);
        Page<SmsregistercodeView> pageVo = (Page<SmsregistercodeView>)page.convert(SmsregistercodeView::new);
        return new PageUtils(pageVo);
 	}

    
    @Override
	public List<SmsregistercodeVO> selectListVO(QueryWrapper<SmsregistercodeEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public SmsregistercodeVO selectVO(QueryWrapper<SmsregistercodeEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<SmsregistercodeView> selectListView(QueryWrapper<SmsregistercodeEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public SmsregistercodeView selectView(QueryWrapper<SmsregistercodeEntity> wrapper) {
        SmsregistercodeEntity entity = baseMapper.selectOne(wrapper);
        if (null != entity) {
            return new SmsregistercodeView(entity);
        } else {
            return null;
        }
	}


}
