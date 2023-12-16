package com.example.quiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@ Annotation 註釋

//讓 spring boot 做託管
//Entity:DB和程式碼的中介(為一個容器)
@Entity

//讓 spring boot 知道和 DB 哪張表做連結
//name = DB資料表名稱
@Table(name="user")
public class User {
	
	//PK 標示 
	@Id
	//DB 哪個欄位做連結
	@Column(name="account")
	private String account;
	
	//因password會和機密較有關，故用pwd代替
	@Column(name="password")
	private String pwd;
	
	public User() {
		super();

	}

	public User(String account, String pwd) {
		super();
		this.account = account;
		this.pwd = pwd;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
