package com.yb.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.utils.PageUtils;
import com.yb.utils.Query;


import com.yb.dao.ExamquestionbankDao;
import com.yb.entity.ExamquestionbankEntity;
import com.yb.service.ExamquestionbankService;
import com.yb.entity.vo.ExamquestionbankVO;
import com.yb.entity.view.ExamquestionbankView;

@Service("examquestionbankService")
public class ExamquestionbankServiceImpl extends ServiceImpl<ExamquestionbankDao, ExamquestionbankEntity> implements ExamquestionbankService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ExamquestionbankEntity> page = baseMapper.selectPage(new Query<ExamquestionbankEntity>(params).getPage(),new QueryWrapper<ExamquestionbankEntity>());
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, QueryWrapper<ExamquestionbankEntity> wrapper) {
        Page<ExamquestionbankEntity> page = baseMapper.selectPage(new Query<ExamquestionbankEntity>(params).getPage(),wrapper);
        Page<ExamquestionbankView> pageVo = (Page<ExamquestionbankView>)page.convert(ExamquestionbankView::new);
        return new PageUtils(pageVo);
 	}

    
    @Override
	public List<ExamquestionbankVO> selectListVO(QueryWrapper<ExamquestionbankEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ExamquestionbankVO selectVO(QueryWrapper<ExamquestionbankEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ExamquestionbankView> selectListView(QueryWrapper<ExamquestionbankEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ExamquestionbankView selectView(QueryWrapper<ExamquestionbankEntity> wrapper) {
        ExamquestionbankEntity entity = baseMapper.selectOne(wrapper);
        if (null != entity) {
            return new ExamquestionbankView(entity);
        } else {
            return null;
        }
	}


}
