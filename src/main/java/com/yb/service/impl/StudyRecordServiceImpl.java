package com.yb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yb.dao.StudyRecordDao;
import com.yb.entity.StudyRecordEntity;
import com.yb.service.StudyRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("studyRecordService")
public class StudyRecordServiceImpl extends ServiceImpl<StudyRecordDao, StudyRecordEntity> implements StudyRecordService {

	@Override
	public Map<String, Object> getTotalWords() {
		return baseMapper.selectTotalWords();
	}

	@Override
	public List<Map<String, Object>> getModuleTime() {
		return baseMapper.selectModuleTime();
	}

	@Override
	public List<Map<String, Object>> getTop5() {
		return baseMapper.selectTop5();
	}
}
