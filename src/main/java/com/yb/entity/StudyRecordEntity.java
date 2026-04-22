package com.yb.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@TableName("study_record")
public class StudyRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;

	private String xueshengZhanghao;

	private String module;

	private Integer studyTime;

	private Integer wordCount;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date createTime;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date updateTime;
}
