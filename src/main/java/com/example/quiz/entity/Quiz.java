package com.example.quiz.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quiz")
public class Quiz {

	// 讓資料存進DB後，可以得到AI(Auto Increment)欄位的值
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "num")
	private int num;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	// List集合(相同資料型態放一起)
	// <資料型態>
	@Column(name = "questions")
	private String questionList;

	@Column(name = "is_published")
	private boolean published;
	
	public Quiz() {
		super();
	}

//	public Quiz(int num2, String name2, String description2, LocalDate startDate2, LocalDate endDate2, String str,
//			boolean published2) {
//		super();
//	}

	public Quiz(String name, String description, LocalDate startDate, LocalDate endDate, String questionList,
			boolean published) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.questionList = questionList;
		this.published = published;
	}

	

	public Quiz(int num, String name, String description, LocalDate startDate, LocalDate endDate, String questionList,
		boolean published) {
	super();
	this.num = num;
	this.name = name;
	this.description = description;
	this.startDate = startDate;
	this.endDate = endDate;
	this.questionList = questionList;
	this.published = published;
}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public String getQuestionList() {
		return questionList;
	}

	public void setQuestionList(String questionList) {
		this.questionList = questionList;
	}
	
	


	
	

}
