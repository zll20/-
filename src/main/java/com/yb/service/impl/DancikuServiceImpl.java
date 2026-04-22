package com.yb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;


import com.yb.dao.DancikuDao;
import com.yb.entity.DancikuEntity;
import com.yb.service.DancikuService;
import com.yb.entity.vo.DancikuVO;
import com.yb.entity.view.DancikuView;

@Service("dancikuService")
public class DancikuServiceImpl extends ServiceImpl<DancikuDao, DancikuEntity> implements DancikuService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DancikuEntity> page = baseMapper.selectPage(new Query<DancikuEntity>(params).getPage(),new QueryWrapper<DancikuEntity>());
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, QueryWrapper<DancikuEntity> wrapper) {
        Page<DancikuEntity> page = baseMapper.selectPage(new Query<DancikuEntity>(params).getPage(),wrapper);
        Page<DancikuView> pageVo = (Page<DancikuView>)page.convert(DancikuView::new);
        return new PageUtils(pageVo);
 	}

    
    @Override
	public List<DancikuVO> selectListVO(QueryWrapper<DancikuEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public DancikuVO selectVO(QueryWrapper<DancikuEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<DancikuView> selectListView(QueryWrapper<DancikuEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public DancikuView selectView(QueryWrapper<DancikuEntity> wrapper) {
        DancikuEntity entity = baseMapper.selectOne(wrapper);
        if (null != entity) {
            return new DancikuView(entity);
        } else {
            return null;
        }
	}


}
