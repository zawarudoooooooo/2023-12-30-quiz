package com.example.quiz.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizSearchReq {
	
	@JsonProperty("quiz_name")
	private String quizName;
	
	@JsonProperty("start_date")
	private LocalDate startDate;
	
	@JsonProperty("end_date")
	private LocalDate endDate;
	
	@JsonProperty("is_login")
	private boolean login;

	public QuizSearchReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuizSearchReq(String quizName, LocalDate startDate, LocalDate endDate, boolean login) {
		super();
		this.quizName = quizName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.login = login;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}


}
