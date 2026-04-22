package com.yb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;


import com.yb.dao.ExamquestionDao;
import com.yb.entity.ExamquestionEntity;
import com.yb.service.ExamquestionService;
import com.yb.entity.vo.ExamquestionVO;
import com.yb.entity.view.ExamquestionView;

@Service("examquestionService")
public class ExamquestionServiceImpl extends ServiceImpl<ExamquestionDao, ExamquestionEntity> implements ExamquestionService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExamquestionEntity> page = baseMapper.selectPage(new Query<ExamquestionEntity>(params).getPage(),new QueryWrapper<ExamquestionEntity>());
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, QueryWrapper<ExamquestionEntity> wrapper) {
        Page<ExamquestionEntity> page = baseMapper.selectPage(new Query<ExamquestionEntity>(params).getPage(),wrapper);
        Page<ExamquestionView> pageVo = (Page<ExamquestionView>)page.convert(ExamquestionView::new);
        return new PageUtils(pageVo);
 	}

    
    @Override
	public List<ExamquestionVO> selectListVO(QueryWrapper<ExamquestionEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ExamquestionVO selectVO(QueryWrapper<ExamquestionEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ExamquestionView> selectListView(QueryWrapper<ExamquestionEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ExamquestionView selectView(QueryWrapper<ExamquestionEntity> wrapper) {
        ExamquestionEntity entity = baseMapper.selectOne(wrapper);
        if (null != entity) {
            return new ExamquestionView(entity);
        } else {
            return null;
        }
	}


}
