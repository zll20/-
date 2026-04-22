package com.yb.entity.vo;

import com.yb.entity.SmsregistercodeEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 短信验证码
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
public class SmsregistercodeVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 角色
	 */
	
	private String role;
		
	/**
	 * 验证码
	 */
	
	private String code;
				
	
	/**
	 * 设置：角色
	 */
	 
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * 获取：角色
	 */
	public String getRole() {
		return role;
	}
				
	
	/**
	 * 设置：验证码
	 */
	 
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 获取：验证码
	 */
	public String getCode() {
		return code;
	}
			
}
