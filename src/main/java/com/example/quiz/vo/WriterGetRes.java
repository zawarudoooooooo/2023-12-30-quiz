package com.example.quiz.vo;

import java.util.List;

import com.example.quiz.entity.Writer;

public class WriterGetRes extends QuizRes {
	
	private List<Writer> writerList;

	public WriterGetRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WriterGetRes(int code, String message, List<Writer> writerList) {
		super(code, message);
		this.writerList = writerList;
		// TODO Auto-generated constructor stub
	}

	public List<Writer> getWriterList() {
		return writerList;
	}

	public void setWriterList(List<Writer> writerList) {
		this.writerList = writerList;
	}
}
