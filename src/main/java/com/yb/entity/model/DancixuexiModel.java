package com.yb.entity.model;

import com.yb.entity.DancixuexiEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 单词学习
 * 接收传参的实体类  
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了） 
 * 取自ModelAndView 的model名称
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
public class DancixuexiModel  implements Serializable {
	private static final long serialVersionUID = 1L;

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
