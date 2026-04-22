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
 * 互动学习
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
@TableName("forum")
@Data
public class ForumEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public ForumEntity() {
		
	}
	
	public ForumEntity(T t) {
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
	 * 帖子标题
	 */
	private String title;
	
	/**
	 * 帖子内容
	 */
	private String content;
	
	/**
	 * 父节点id
	 */
	private Long parentid;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 头像
	 */
	private String avatarurl;
	
	/**
	 * 状态
	 */
	private String isdone;
	
	/**
	 * 是否置顶
	 */
	private Integer istop;
	
	/**
	 * 置顶时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date toptime;
	
	/**
	 * 封面
	 */
	private String cover;
	
	/**
	 * 是否匿名(1:是,0:否)
	 */
	private Integer isanon;
	
	/**
	 * 是否删除(1:是,0:否)
	 */
	private Integer delflag;
	
	/**
	 * 是否审核
	 */
	private String sfsh;
	
	/**
	 * 审核回复
	 */
	private String shhf;
	
	/**
	 * 用户id
	 */
	private Long userid;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	@TableField(exist = false)
	private List<ForumEntity> childs;
	
}
