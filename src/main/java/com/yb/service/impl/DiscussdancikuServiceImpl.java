package com.yb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;


import com.yb.dao.DiscussdancikuDao;
import com.yb.entity.DiscussdancikuEntity;
import com.yb.service.DiscussdancikuService;
import com.yb.entity.vo.DiscussdancikuVO;
import com.yb.entity.view.DiscussdancikuView;

@Service("discussdancikuService")
public class DiscussdancikuServiceImpl extends ServiceImpl<DiscussdancikuDao, DiscussdancikuEntity> implements DiscussdancikuService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DiscussdancikuEntity> page = baseMapper.selectPage(new Query<DiscussdancikuEntity>(params).getPage(),new QueryWrapper<DiscussdancikuEntity>());
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, QueryWrapper<DiscussdancikuEntity> wrapper) {
        Page<DiscussdancikuEntity> page = baseMapper.selectPage(new Query<DiscussdancikuEntity>(params).getPage(),wrapper);
        Page<DiscussdancikuView> pageVo = (Page<DiscussdancikuView>)page.convert(DiscussdancikuView::new);
        return new PageUtils(pageVo);
 	}

    
    @Override
	public List<DiscussdancikuVO> selectListVO(QueryWrapper<DiscussdancikuEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public DiscussdancikuVO selectVO(QueryWrapper<DiscussdancikuEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<DiscussdancikuView> selectListView(QueryWrapper<DiscussdancikuEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public DiscussdancikuView selectView(QueryWrapper<DiscussdancikuEntity> wrapper) {
        DiscussdancikuEntity entity = baseMapper.selectOne(wrapper);
        if (null != entity) {
            return new DiscussdancikuView(entity);
        } else {
            return null;
        }
	}


}
