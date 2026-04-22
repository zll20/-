package com.yb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.entity.StudyRecordEntity;

import java.util.List;
import java.util.Map;

public interface StudyRecordService extends IService<StudyRecordEntity> {

	Map<String, Object> getTotalWords();

	List<Map<String, Object>> getModuleTime();

	List<Map<String, Object>> getTop5();
}
