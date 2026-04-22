package com.yb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;


import com.yb.dao.ForumDao;
import com.yb.entity.ForumEntity;
import com.yb.service.ForumService;
import com.yb.entity.vo.ForumVO;
import com.yb.entity.view.ForumView;

@Service("forumService")
public class ForumServiceImpl extends ServiceImpl<ForumDao, ForumEntity> implements ForumService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ForumEntity> page = baseMapper.selectPage(new Query<ForumEntity>(params).getPage(),new QueryWrapper<ForumEntity>());
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, QueryWrapper<ForumEntity> wrapper) {
        Page<ForumEntity> page = baseMapper.selectPage(new Query<ForumEntity>(params).getPage(),wrapper);
        Page<ForumView> pageVo = (Page<ForumView>)page.convert(ForumView::new);
        return new PageUtils(pageVo);
 	}

    
    @Override
	public List<ForumVO> selectListVO(QueryWrapper<ForumEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ForumVO selectVO(QueryWrapper<ForumEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ForumView> selectListView(QueryWrapper<ForumEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ForumView selectView(QueryWrapper<ForumEntity> wrapper) {
        ForumEntity entity = baseMapper.selectOne(wrapper);
        if (null != entity) {
            return new ForumView(entity);
        } else {
            return null;
        }
	}


}
