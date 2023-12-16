package com.example.quiz.service.ifs;

import com.example.quiz.vo.UserLoginRes;

public interface UserService {
	
//定義方法 public void login();
	
	
//權限,回傳值型態,方法名稱,(參數資料型態,帶入參數)
	public UserLoginRes login (String account,String pwd);
	
	public UserLoginRes create(String account,String pwd);

}
