//Rtn Code : return code(常數)
//回應代碼和錯誤訊息
// enum 列舉 : 將使用者可能發生的行為，列舉出來

package com.example.quiz.constants;

public enum RtnCode {
	//enum 固定格式:參數(code,message)
	SUCCESSFUL(200,"Seccessful"),//
	PARAM_ERROR(400,"Param error"),//
	ACCOUNT_NOT_FOUND(404,"Account not found"),//
	DATE_FORMAT_ERROR(400,"Date format error"),//
	ACCOUNT_EXISTED(400,"Accpunt exsisted"),//
	QUESTION_IS_EMPTY(400,"Question is empty"),//
	QUESTION_PARAM_ERROR(400,"Question param error"),//
	QUIZ_CREATE_ERROR(400,"Quiz create error"),//
	QUIZ_NOT_FOUND(400,"Quiz not found"),//
	QUIZ_CANNOT_BE_UPDATED(400,"Quiz cannot be updated"),//
	QUIZ_ID_LIST_IS_EMPTY(400,"Quiz id list is empty"),//
	QUESTION_DELETE_ERROR(400,"Question delete error"),//
	NO_QUESTION_ANSWER(400,"No question answer"),//
	STRING_PARSER_ERROR(400,"String parser error"),//
	PLEASE_LOGIN_FIRST(400,"Please Login First"); 
	
	//屬性
	private int code;
	private String message;
	
	//建構方法
	private RtnCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}
