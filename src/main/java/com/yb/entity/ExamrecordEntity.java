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
 * 考试记录
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
@TableName("examrecord")
@Data
public class ExamrecordEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public ExamrecordEntity() {
		
	}
	
	public ExamrecordEntity(T t) {
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
	 * 用户名
	 */
	private String username;
	
	/**
	 * 试卷id
	 */
	private Long paperid;
	
	/**
	 * 试卷名称
	 */
	private String papername;
	
	/**
	 * 试题id
	 */
	private Long questionid;
	
	/**
	 * 试题名称
	 */
	private String questionname;
	
	/**
	 * 试题类型
	 */
	private Long type;
	
	/**
	 * 是否批卷
	 */
	private Long ismark;
	
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
	 * 试题得分
	 */
	private Long myscore;
	
	/**
	 * 考生答案
	 */
	private String myanswer;
	
	/**
	 * 考试编号
	 */
	private String examno;
	
	/**
	 * 用户id
	 */
	private Long userid;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
