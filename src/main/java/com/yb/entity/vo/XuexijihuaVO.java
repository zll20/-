package com.yb.entity.vo;

import com.yb.entity.XuexijihuaEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 学习计划
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public class XuexijihuaVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 学号
	 */
	
	private String xuehao;
		
	/**
	 * 日期
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date riqi;
		
	/**
	 * 学习时长
	 */
	
	private Double xuexishizhang;
		
	/**
	 * 学习任务
	 */
	
	private String xuexirenwu;
		
	/**
	 * 复习计划
	 */
	
	private String fuxijihua;
				
	
	/**
	 * 设置：学号
	 */
	 
	public void setXuehao(String xuehao) {
		this.xuehao = xuehao;
	}
	
	/**
	 * 获取：学号
	 */
	public String getXuehao() {
		return xuehao;
	}
				
	
	/**
	 * 设置：日期
	 */
	 
	public void setRiqi(Date riqi) {
		this.riqi = riqi;
	}
	
	/**
	 * 获取：日期
	 */
	public Date getRiqi() {
		return riqi;
	}
				
	
	/**
	 * 设置：学习时长
	 */
	 
	public void setXuexishizhang(Double xuexishizhang) {
		this.xuexishizhang = xuexishizhang;
	}
	
	/**
	 * 获取：学习时长
	 */
	public Double getXuexishizhang() {
		return xuexishizhang;
	}
				
	
	/**
	 * 设置：学习任务
	 */
	 
	public void setXuexirenwu(String xuexirenwu) {
		this.xuexirenwu = xuexirenwu;
	}
	
	/**
	 * 获取：学习任务
	 */
	public String getXuexirenwu() {
		return xuexirenwu;
	}
				
	
	/**
	 * 设置：复习计划
	 */
	 
	public void setFuxijihua(String fuxijihua) {
		this.fuxijihua = fuxijihua;
	}
	
	/**
	 * 获取：复习计划
	 */
	public String getFuxijihua() {
		return fuxijihua;
	}
			
}
