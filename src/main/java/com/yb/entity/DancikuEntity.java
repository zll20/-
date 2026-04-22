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
 * 单词库
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
@TableName("danciku")
@Data
public class DancikuEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public DancikuEntity() {
		
	}
	
	public DancikuEntity(T t) {
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
	 * 单词
	 */
	private String danci;
	
	/**
	 * 级别
	 */
	private String jibie;
	
	/**
	 * 单词分类
	 */
	private String dancifenlei;
	
	/**
	 * 释义
	 */
	private String shiyi;
	
	/**
	 * 例句
	 */
	private String liju;
	
	/**
	 * 封面图
	 */
	private String fengmiantu;
	
	/**
	 * 读音
	 */
	private String duyin;
	
	/**
	 * 最近点击时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date clicktime;
	
	/**
	 * 评论数
	 */
	private Integer discussnum;
	
	/**
	 * 收藏数
	 */
	private Integer storeupnum;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
