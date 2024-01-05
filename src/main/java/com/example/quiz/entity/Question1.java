package com.example.quiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="question")
//和 QeustionId Class 做連結
@IdClass(value=Question1Id.class)
public class Question1 {

	//@Id超過兩個(包含)要生class來管理
	@Id
	@Column(name="quiz_num")
	private int quizNum;
	
	@Id
	@Column(name="num")
	private int num;
	
	@Column(name="title")
	private String title;
	
	@Column(name="type")
	private String type;
	
	@Column(name="is_necessary")
	private boolean necessary;
	
	@Column(name="option")
	private String option;
	
	@Column(name="is_published")
	private boolean published;

	public Question1() {
		super();
	}

	public Question1(int quizNum, int num, String title, String type, boolean necessary, String option,
			boolean published) {
		super();
		this.quizNum = quizNum;
		this.num = num;
		this.title = title;
		this.type = type;
		this.necessary = necessary;
		this.option = option;
		this.published = published;
	}

	public int getQuizNum() {
		return quizNum;
	}

	public void setQuizNum(int quizNum) {
		this.quizNum = quizNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNecessary() {
		return necessary;
	}

	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}
	
}
