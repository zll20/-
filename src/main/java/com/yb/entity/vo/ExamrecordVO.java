package com.yb.entity.vo;

import com.yb.entity.ExamrecordEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 考试记录
 * @author 
 * @email 
 * @date 2026-03-14 23:57:25
 */
public class ExamrecordVO  implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 试卷id
	 */
	
	private Long paperid;
		
	/**
	 * 试卷名称
	 */
	
	private String papername;
		
	/**
	 * 试题id
	 */
	
	private Long questionid;
		
	/**
	 * 试题名称
	 */
	
	private String questionname;
		
	/**
	 * 试题类型
	 */
	
	private Long type;
		
	/**
	 * 是否批卷
	 */
	
	private Long ismark;
		
	/**
	 * 选项，json字符串
	 */
	
	private String options;
		
	/**
	 * 分值
	 */
	
	private Long score;
		
	/**
	 * 正确答案
	 */
	
	private String answer;
		
	/**
	 * 答案解析
	 */
	
	private String analysis;
		
	/**
	 * 试题得分
	 */
	
	private Long myscore;
		
	/**
	 * 考生答案
	 */
	
	private String myanswer;
		
	/**
	 * 考试编号
	 */
	
	private String examno;
		
	/**
	 * 用户id
	 */
	
	private Long userid;
				
	
	/**
	 * 设置：试卷id
	 */
	 
	public void setPaperid(Long paperid) {
		this.paperid = paperid;
	}
	
	/**
	 * 获取：试卷id
	 */
	public Long getPaperid() {
		return paperid;
	}
				
	
	/**
	 * 设置：试卷名称
	 */
	 
	public void setPapername(String papername) {
		this.papername = papername;
	}
	
	/**
	 * 获取：试卷名称
	 */
	public String getPapername() {
		return papername;
	}
				
	
	/**
	 * 设置：试题id
	 */
	 
	public void setQuestionid(Long questionid) {
		this.questionid = questionid;
	}
	
	/**
	 * 获取：试题id
	 */
	public Long getQuestionid() {
		return questionid;
	}
				
	
	/**
	 * 设置：试题名称
	 */
	 
	public void setQuestionname(String questionname) {
		this.questionname = questionname;
	}
	
	/**
	 * 获取：试题名称
	 */
	public String getQuestionname() {
		return questionname;
	}
				
	
	/**
	 * 设置：试题类型
	 */
	 
	public void setType(Long type) {
		this.type = type;
	}
	
	/**
	 * 获取：试题类型
	 */
	public Long getType() {
		return type;
	}
				
	
	/**
	 * 设置：是否批卷
	 */
	 
	public void setIsmark(Long ismark) {
		this.ismark = ismark;
	}
	
	/**
	 * 获取：是否批卷
	 */
	public Long getIsmark() {
		return ismark;
	}
				
	
	/**
	 * 设置：选项，json字符串
	 */
	 
	public void setOptions(String options) {
		this.options = options;
	}
	
	/**
	 * 获取：选项，json字符串
	 */
	public String getOptions() {
		return options;
	}
				
	
	/**
	 * 设置：分值
	 */
	 
	public void setScore(Long score) {
		this.score = score;
	}
	
	/**
	 * 获取：分值
	 */
	public Long getScore() {
		return score;
	}
				
	
	/**
	 * 设置：正确答案
	 */
	 
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	/**
	 * 获取：正确答案
	 */
	public String getAnswer() {
		return answer;
	}
				
	
	/**
	 * 设置：答案解析
	 */
	 
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	
	/**
	 * 获取：答案解析
	 */
	public String getAnalysis() {
		return analysis;
	}
				
	
	/**
	 * 设置：试题得分
	 */
	 
	public void setMyscore(Long myscore) {
		this.myscore = myscore;
	}
	
	/**
	 * 获取：试题得分
	 */
	public Long getMyscore() {
		return myscore;
	}
				
	
	/**
	 * 设置：考生答案
	 */
	 
	public void setMyanswer(String myanswer) {
		this.myanswer = myanswer;
	}
	
	/**
	 * 获取：考生答案
	 */
	public String getMyanswer() {
		return myanswer;
	}
				
	
	/**
	 * 设置：考试编号
	 */
	 
	public void setExamno(String examno) {
		this.examno = examno;
	}
	
	/**
	 * 获取：考试编号
	 */
	public String getExamno() {
		return examno;
	}
				
	
	/**
	 * 设置：用户id
	 */
	 
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	/**
	 * 获取：用户id
	 */
	public Long getUserid() {
		return userid;
	}
			
}
