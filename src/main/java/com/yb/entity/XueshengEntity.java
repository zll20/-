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
 * 学生
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-03-14 23:57:23
 */
@TableName("xuesheng")
@Data
public class XueshengEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public XueshengEntity() {
		
	}
	
	public XueshengEntity(T t) {
		try {
			BeanUtils.copyProperties(t,this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 主键id
	 */
    @TableId
	private Long id;
	
	/**
	 * 账号
	 */
	private String zhanghao;
	
	/**
	 * 昵称
	 */
	private String nicheng;
	
	/**
	 * 密码
	 */
	private String mima;
	
	/**
	 * 姓名
	 */
	private String xingming;
	
	/**
	 * 性别
	 */
	private String xingbie;
	
	/**
	 * 头像
	 */
	private String touxiang;
	
	/**
	 * 邮箱
	 */
	private String youxiang;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

}
