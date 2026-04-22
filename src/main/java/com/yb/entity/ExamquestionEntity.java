package com.yb.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.*;
import cn.hutool.core.annotation.Alias;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 试题
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
@TableName("examquestion")
@Data
public class ExamquestionEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public ExamquestionEntity() {
		
	}
	
	public ExamquestionEntity(T t) {
		try {
			BeanUtils.copyProperties(t,this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 主键id
	 */
    @TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 试卷id
	 */
	private Long paperid;
	
	/**
	 * 试卷名称
	 */
	private String papername;
	
	/**
	 * 试题名称
	 */
	private String questionname;
	
	/**
	 * 选项，json字符串
	 */
	private String options;
	
	/**
	 * 分值
	 */
	private Long score;
	
	/**
	 * 正确答案
	 */
	private String answer;
	
	/**
	 * 答案解析
	 */
	private String analysis;
	
	/**
	 * 试题类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空）4:主观题
	 */
	private Long type;
	
	/**
	 * 试题排序，值越大排越前面
	 */
	private Long sequence;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
