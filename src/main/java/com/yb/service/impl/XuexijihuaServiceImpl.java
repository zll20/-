package com.yb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;


import com.yb.dao.XuexijihuaDao;
import com.yb.entity.XuexijihuaEntity;
import com.yb.service.XuexijihuaService;
import com.yb.entity.vo.XuexijihuaVO;
import com.yb.entity.view.XuexijihuaView;

@Service("xuexijihuaService")
public class XuexijihuaServiceImpl extends ServiceImpl<XuexijihuaDao, XuexijihuaEntity> implements XuexijihuaService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XuexijihuaEntity> page = baseMapper.selectPage(new Query<XuexijihuaEntity>(params).getPage(),new QueryWrapper<XuexijihuaEntity>());
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, QueryWrapper<XuexijihuaEntity> wrapper) {
        Page<XuexijihuaEntity> page = baseMapper.selectPage(new Query<XuexijihuaEntity>(params).getPage(),wrapper);
        Page<XuexijihuaView> pageVo = (Page<XuexijihuaView>)page.convert(XuexijihuaView::new);
        return new PageUtils(pageVo);
 	}

    
    @Override
	public List<XuexijihuaVO> selectListVO(QueryWrapper<XuexijihuaEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public XuexijihuaVO selectVO(QueryWrapper<XuexijihuaEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<XuexijihuaView> selectListView(QueryWrapper<XuexijihuaEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public XuexijihuaView selectView(QueryWrapper<XuexijihuaEntity> wrapper) {
        XuexijihuaEntity entity = baseMapper.selectOne(wrapper);
        if (null != entity) {
            return new XuexijihuaView(entity);
        } else {
            return null;
        }
	}


}
