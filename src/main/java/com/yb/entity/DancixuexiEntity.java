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
 * 单词学习
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
@TableName("dancixuexi")
@Data
public class DancixuexiEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public DancixuexiEntity() {
		
	}
	
	public DancixuexiEntity(T t) {
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
	 * 姓名
	 */
	private String xingming;
	
	/**
	 * 日期
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat
	private Date riqi;
	
	/**
	 * 学习时长
	 */
	private Double xuexishizhang;
	
	/**
	 * 单词量
	 */
	private Integer danciliang;
	
	/**
	 * 是否记住
	 */
	private String shifoujizhu;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
