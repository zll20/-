package com.yb.entity.view;

import com.yb.entity.SmsregistercodeEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.yb.utils.EncryptUtil;
 

/**
 * 短信验证码
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
@TableName("smsregistercode")
public class SmsregistercodeView  extends SmsregistercodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public SmsregistercodeView(){
	}
 
 	public SmsregistercodeView(SmsregistercodeEntity smsregistercodeEntity){
 	try {
			BeanUtils.copyProperties(smsregistercodeEntity,this);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
