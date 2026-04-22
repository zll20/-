package com.yb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yb.entity.StudyRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface StudyRecordDao extends BaseMapper<StudyRecordEntity> {

	@Select("SELECT SUM(word_count) as totalWords FROM study_record")
	Map<String, Object> selectTotalWords();

	@Select("SELECT module, SUM(study_time) as totalTime FROM study_record GROUP BY module")
	List<Map<String, Object>> selectModuleTime();

	@Select("SELECT s.xuesheng_zhanghao as zhanghao, x.xingming, " +
			"SUM(s.study_time) as totalTime, SUM(s.word_count) as totalWords " +
			"FROM study_record s " +
			"LEFT JOIN xuesheng x ON s.xuesheng_zhanghao = x.zhanghao " +
			"GROUP BY s.xuesheng_zhanghao, x.xingming " +
			"ORDER BY totalTime DESC LIMIT 5")
	List<Map<String, Object>> selectTop5();
}
