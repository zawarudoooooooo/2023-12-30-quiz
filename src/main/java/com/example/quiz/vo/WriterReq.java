package com.example.quiz.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WriterReq {
	@JsonProperty(value="quiz_num")
	private int quizNum;
	
	private String name;
	
	private String phone;
	
	private String email;
	
	private int age;
	
	//前端傳過來的是JSON格式的字串
	//""
	private String answer;

	public WriterReq() {
		super();
	}

	public WriterReq(int quizNum, String name, String phone, String email, int age, String answer) {
		super();
		this.quizNum = quizNum;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.answer = answer;
	}

	public int getQuizNum() {
		return quizNum;
	}

	public void setQuizNum(int quizNum) {
		this.quizNum = quizNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
