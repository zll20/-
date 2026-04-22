package com.yb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;


import com.yb.dao.PopupremindDao;
import com.yb.entity.PopupremindEntity;
import com.yb.service.PopupremindService;
import com.yb.entity.vo.PopupremindVO;
import com.yb.entity.view.PopupremindView;

@Service("popupremindService")
public class PopupremindServiceImpl extends ServiceImpl<PopupremindDao, PopupremindEntity> implements PopupremindService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PopupremindEntity> page = baseMapper.selectPage(new Query<PopupremindEntity>(params).getPage(),new QueryWrapper<PopupremindEntity>());
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, QueryWrapper<PopupremindEntity> wrapper) {
        Page<PopupremindEntity> page = baseMapper.selectPage(new Query<PopupremindEntity>(params).getPage(),wrapper);
        Page<PopupremindView> pageVo = (Page<PopupremindView>)page.convert(PopupremindView::new);
        return new PageUtils(pageVo);
 	}

    
    @Override
	public List<PopupremindVO> selectListVO(QueryWrapper<PopupremindEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public PopupremindVO selectVO(QueryWrapper<PopupremindEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<PopupremindView> selectListView(QueryWrapper<PopupremindEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public PopupremindView selectView(QueryWrapper<PopupremindEntity> wrapper) {
        PopupremindEntity entity = baseMapper.selectOne(wrapper);
        if (null != entity) {
            return new PopupremindView(entity);
        } else {
            return null;
        }
	}


}
