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
 * 弹窗提醒
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
@TableName("popupremind")
@Data
public class PopupremindEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public PopupremindEntity() {
		
	}
	
	public PopupremindEntity(T t) {
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
	 * 发布人id
	 */
	private Long userid;
	
	/**
	 * 角色
	 */
	private String role;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 简介
	 */
	private String brief;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 提醒时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date remindtime;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
