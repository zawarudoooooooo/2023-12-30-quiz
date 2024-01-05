package com.example.quiz.entity;

import java.io.Serializable;

//implements Serializable 實作序列化
@SuppressWarnings("serial")
public class Question1Id implements Serializable {

	private int quizNum;
	
	private int num;
	
	public Question1Id() {
		super();
	}
	
	public Question1Id(int quizNum, int num) {
		super();
		this.quizNum = quizNum;
		this.num = num;
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
}
