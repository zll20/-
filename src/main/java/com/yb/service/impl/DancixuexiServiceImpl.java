package com.yb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;


import com.yb.dao.DancixuexiDao;
import com.yb.entity.DancixuexiEntity;
import com.yb.service.DancixuexiService;
import com.yb.entity.vo.DancixuexiVO;
import com.yb.entity.view.DancixuexiView;

@Service("dancixuexiService")
public class DancixuexiServiceImpl extends ServiceImpl<DancixuexiDao, DancixuexiEntity> implements DancixuexiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DancixuexiEntity> page = baseMapper.selectPage(new Query<DancixuexiEntity>(params).getPage(),new QueryWrapper<DancixuexiEntity>());
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, QueryWrapper<DancixuexiEntity> wrapper) {
        Page<DancixuexiEntity> page = baseMapper.selectPage(new Query<DancixuexiEntity>(params).getPage(),wrapper);
        Page<DancixuexiView> pageVo = (Page<DancixuexiView>)page.convert(DancixuexiView::new);
        return new PageUtils(pageVo);
 	}

    
    @Override
	public List<DancixuexiVO> selectListVO(QueryWrapper<DancixuexiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public DancixuexiVO selectVO(QueryWrapper<DancixuexiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<DancixuexiView> selectListView(QueryWrapper<DancixuexiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public DancixuexiView selectView(QueryWrapper<DancixuexiEntity> wrapper) {
        DancixuexiEntity entity = baseMapper.selectOne(wrapper);
        if (null != entity) {
            return new DancixuexiView(entity);
        } else {
            return null;
        }
	}

    @Override
    public List<Map<String, Object>> selectValue(Map<String, Object> params, QueryWrapper<DancixuexiEntity> wrapper) {
        return baseMapper.selectValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params, QueryWrapper<DancixuexiEntity> wrapper) {
        return baseMapper.selectTimeStatValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectGroup(Map<String, Object> params, QueryWrapper<DancixuexiEntity> wrapper) {
        return baseMapper.selectGroup(params, wrapper);
    }




}
