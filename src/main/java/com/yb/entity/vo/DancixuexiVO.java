package com.yb.entity.vo;

import com.yb.entity.DancixuexiEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 单词学习
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public class DancixuexiVO  implements Serializable {
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
	 * 单词量
	 */
	
	private Integer danciliang;
		
	/**
	 * 是否记住
	 */
	
	private String shifoujizhu;
				
	
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
	 * 设置：单词量
	 */
	 
	public void setDanciliang(Integer danciliang) {
		this.danciliang = danciliang;
	}
	
	/**
	 * 获取：单词量
	 */
	public Integer getDanciliang() {
		return danciliang;
	}
				
	
	/**
	 * 设置：是否记住
	 */
	 
	public void setShifoujizhu(String shifoujizhu) {
		this.shifoujizhu = shifoujizhu;
	}
	
	/**
	 * 获取：是否记住
	 */
	public String getShifoujizhu() {
		return shifoujizhu;
	}
			
}
