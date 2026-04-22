package com.yb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;


import com.yb.dao.ExampaperDao;
import com.yb.entity.ExampaperEntity;
import com.yb.service.ExampaperService;
import com.yb.entity.vo.ExampaperVO;
import com.yb.entity.view.ExampaperView;

@Service("exampaperService")
public class ExampaperServiceImpl extends ServiceImpl<ExampaperDao, ExampaperEntity> implements ExampaperService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExampaperEntity> page = baseMapper.selectPage(new Query<ExampaperEntity>(params).getPage(),new QueryWrapper<ExampaperEntity>());
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, QueryWrapper<ExampaperEntity> wrapper) {
        Page<ExampaperEntity> page = baseMapper.selectPage(new Query<ExampaperEntity>(params).getPage(),wrapper);
        Page<ExampaperView> pageVo = (Page<ExampaperView>)page.convert(ExampaperView::new);
        return new PageUtils(pageVo);
 	}

    
    @Override
	public List<ExampaperVO> selectListVO(QueryWrapper<ExampaperEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ExampaperVO selectVO(QueryWrapper<ExampaperEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ExampaperView> selectListView(QueryWrapper<ExampaperEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ExampaperView selectView(QueryWrapper<ExampaperEntity> wrapper) {
        ExampaperEntity entity = baseMapper.selectOne(wrapper);
        if (null != entity) {
            return new ExampaperView(entity);
        } else {
            return null;
        }
	}


}
