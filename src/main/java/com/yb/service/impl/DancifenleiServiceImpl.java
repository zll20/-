package com.yb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;


import com.yb.dao.DancifenleiDao;
import com.yb.entity.DancifenleiEntity;
import com.yb.service.DancifenleiService;
import com.yb.entity.vo.DancifenleiVO;
import com.yb.entity.view.DancifenleiView;

@Service("dancifenleiService")
public class DancifenleiServiceImpl extends ServiceImpl<DancifenleiDao, DancifenleiEntity> implements DancifenleiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DancifenleiEntity> page = baseMapper.selectPage(new Query<DancifenleiEntity>(params).getPage(),new QueryWrapper<DancifenleiEntity>());
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, QueryWrapper<DancifenleiEntity> wrapper) {
        Page<DancifenleiEntity> page = baseMapper.selectPage(new Query<DancifenleiEntity>(params).getPage(),wrapper);
        Page<DancifenleiView> pageVo = (Page<DancifenleiView>)page.convert(DancifenleiView::new);
        return new PageUtils(pageVo);
 	}

    
    @Override
	public List<DancifenleiVO> selectListVO(QueryWrapper<DancifenleiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public DancifenleiVO selectVO(QueryWrapper<DancifenleiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<DancifenleiView> selectListView(QueryWrapper<DancifenleiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public DancifenleiView selectView(QueryWrapper<DancifenleiEntity> wrapper) {
        DancifenleiEntity entity = baseMapper.selectOne(wrapper);
        if (null != entity) {
            return new DancifenleiView(entity);
        } else {
            return null;
        }
	}


}
