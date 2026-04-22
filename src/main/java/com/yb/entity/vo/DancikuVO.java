package com.yb.entity.vo;

import com.yb.entity.DancikuEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 单词库
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public class DancikuVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
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
				
	
	/**
	 * 设置：级别
	 */
	 
	public void setJibie(String jibie) {
		this.jibie = jibie;
	}
	
	/**
	 * 获取：级别
	 */
	public String getJibie() {
		return jibie;
	}
				
	
	/**
	 * 设置：单词分类
	 */
	 
	public void setDancifenlei(String dancifenlei) {
		this.dancifenlei = dancifenlei;
	}
	
	/**
	 * 获取：单词分类
	 */
	public String getDancifenlei() {
		return dancifenlei;
	}
				
	
	/**
	 * 设置：释义
	 */
	 
	public void setShiyi(String shiyi) {
		this.shiyi = shiyi;
	}
	
	/**
	 * 获取：释义
	 */
	public String getShiyi() {
		return shiyi;
	}
				
	
	/**
	 * 设置：例句
	 */
	 
	public void setLiju(String liju) {
		this.liju = liju;
	}
	
	/**
	 * 获取：例句
	 */
	public String getLiju() {
		return liju;
	}
				
	
	/**
	 * 设置：封面图
	 */
	 
	public void setFengmiantu(String fengmiantu) {
		this.fengmiantu = fengmiantu;
	}
	
	/**
	 * 获取：封面图
	 */
	public String getFengmiantu() {
		return fengmiantu;
	}
				
	
	/**
	 * 设置：最近点击时间
	 */
	 
	public void setClicktime(Date clicktime) {
		this.clicktime = clicktime;
	}
	
	/**
	 * 获取：最近点击时间
	 */
	public Date getClicktime() {
		return clicktime;
	}
				
	
	/**
	 * 设置：评论数
	 */
	 
	public void setDiscussnum(Integer discussnum) {
		this.discussnum = discussnum;
	}
	
	/**
	 * 获取：评论数
	 */
	public Integer getDiscussnum() {
		return discussnum;
	}
				
	
	/**
	 * 设置：收藏数
	 */
	 
	public void setStoreupnum(Integer storeupnum) {
		this.storeupnum = storeupnum;
	}
	
	/**
	 * 获取：收藏数
	 */
	public Integer getStoreupnum() {
		return storeupnum;
	}
			
}
