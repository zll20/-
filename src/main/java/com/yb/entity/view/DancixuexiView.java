package com.yb.entity.view;

import com.yb.entity.DancixuexiEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import java.io.Serializable;
import com.yb.utils.EncryptUtil;
 

/**
 * 单词学习
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2026-03-14 23:57:24
 */
@TableName("dancixuexi")
public class DancixuexiView  extends DancixuexiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public DancixuexiView(){
	}
 
 	public DancixuexiView(DancixuexiEntity dancixuexiEntity){
 	try {
			BeanUtils.copyProperties(dancixuexiEntity,this);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}


}
