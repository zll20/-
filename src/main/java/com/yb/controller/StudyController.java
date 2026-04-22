package com.yb.controller;

import com.yb.annotation.IgnoreAuth;
import com.yb.entity.StudyRecordEntity;
import com.yb.service.StudyRecordService;
import com.yb.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/study")
public class StudyController {

	@Autowired
	private StudyRecordService studyRecordService;

	/**
	 * 保存学习记录
	 */
	@IgnoreAuth
	@RequestMapping("/save")
	public R save(@RequestBody StudyRecordEntity studyRecord) {
		studyRecordService.save(studyRecord);
		return R.ok();
	}

	/**
	 * 学生学习统计
	 */
	@RequestMapping("/statistics")
	public R statistics(@RequestParam String xueshengZhanghao) {
		Map<String, Object> totalWords = studyRecordService.getTotalWords();
		List<Map<String, Object>> moduleTime = studyRecordService.getModuleTime();
		return R.ok().put("totalWords", totalWords).put("moduleTime", moduleTime);
	}

	/**
	 * 管理员：总单词数
	 */
	@IgnoreAuth
	@RequestMapping("/admin/study/totalWords")
	public R adminTotalWords() {
		Map<String, Object> result = studyRecordService.getTotalWords();
		return R.ok().put("data", result);
	}

	/**
	 * 管理员：模块时长统计
	 */
	@IgnoreAuth
	@RequestMapping("/admin/study/moduleTime")
	public R adminModuleTime() {
		List<Map<String, Object>> result = studyRecordService.getModuleTime();
		return R.ok().put("data", result);
	}

	/**
	 * 管理员：学习时长 TOP5
	 */
	@IgnoreAuth
	@RequestMapping("/admin/study/top5")
	public R adminTop5() {
		List<Map<String, Object>> result = studyRecordService.getTop5();
		return R.ok().put("data", result);
	}
}
