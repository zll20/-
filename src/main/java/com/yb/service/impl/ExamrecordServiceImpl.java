package com.yb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;


import com.yb.dao.ExamrecordDao;
import com.yb.entity.ExamrecordEntity;
import com.yb.service.ExamrecordService;
import com.yb.entity.vo.ExamrecordVO;
import com.yb.entity.view.ExamrecordView;

@Service("examrecordService")
public class ExamrecordServiceImpl extends ServiceImpl<ExamrecordDao, ExamrecordEntity> implements ExamrecordService {
	
	@Override
	public PageUtils queryPageGroupBy(Map<String, Object> params, QueryWrapper<ExamrecordEntity> wrapper) {
		Page<ExamrecordView> page =new Query<ExamrecordView>(params).getPage();
        page.setRecords(baseMapper.selectGroupBy(page,wrapper));
    	PageUtils pageUtil = new PageUtils(page);
    	return pageUtil;
	}

    @Override
    public PageUtils queryPageOptionsNum(Map<String, Object> params, QueryWrapper<ExamrecordEntity> wrapper) {
        Page<ExamrecordView> page =new Query<ExamrecordView>(params).getPage();
        page.setRecords(baseMapper.selectOptionsNum(page,wrapper));
        PageUtils pageUtil = new PageUtils(page);
        return pageUtil;
    }
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExamrecordEntity> page = baseMapper.selectPage(new Query<ExamrecordEntity>(params).getPage(),new QueryWrapper<ExamrecordEntity>());
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, QueryWrapper<ExamrecordEntity> wrapper) {
        Page<ExamrecordEntity> page = baseMapper.selectPage(new Query<ExamrecordEntity>(params).getPage(),wrapper);
        Page<ExamrecordView> pageVo = (Page<ExamrecordView>)page.convert(ExamrecordView::new);
        return new PageUtils(pageVo);
 	}

    
    @Override
	public List<ExamrecordVO> selectListVO(QueryWrapper<ExamrecordEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ExamrecordVO selectVO(QueryWrapper<ExamrecordEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ExamrecordView> selectListView(QueryWrapper<ExamrecordEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ExamrecordView selectView(QueryWrapper<ExamrecordEntity> wrapper) {
        ExamrecordEntity entity = baseMapper.selectOne(wrapper);
        if (null != entity) {
            return new ExamrecordView(entity);
        } else {
            return null;
        }
	}


}
