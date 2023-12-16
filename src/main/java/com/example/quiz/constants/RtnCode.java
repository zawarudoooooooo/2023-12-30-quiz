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
	ACCOUNT_EXISTED(400,"Accpunt exsisted"); 
	
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
