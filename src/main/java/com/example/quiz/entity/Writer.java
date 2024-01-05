package com.example.quiz.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.quiz.vo.WriterReq;

@Entity
@Table(name="writer")
public class Writer {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="num")
	private int num;
	
	@Column(name="quiz_num")
	private int quizNum;
	
	@Column(name="name")
	private String name;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="email")
	private String email;
	
	@Column(name="age")
	private int age;
	
	@Column(name="answer")
	private String answer;
	
	@Column(name="write_date_time")
	private LocalDateTime writeDateTime ;

	public Writer() {
		super();
	}

	public Writer(int quizNum, String name, String phone, String email, int age,String answer,LocalDateTime writeDateTime) {
		super();
		this.quizNum = quizNum;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.answer = answer;
		this.writeDateTime = writeDateTime;
	}
	
	public Writer(WriterReq req) {
		super();
		this.quizNum = req.getQuizNum();
		this.name = req.getName();
		this.phone = req.getPhone();
		this.email = req.getEmail();
		this.age = req.getAge();
		this.answer = req.getAnswer();
		this.writeDateTime = LocalDateTime.now();
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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

	public LocalDateTime getWriteDateTime() {
		return writeDateTime;
	}

	public void setWriteDateTime(LocalDateTime writeDateTime) {
		this.writeDateTime = writeDateTime;
	}
}
