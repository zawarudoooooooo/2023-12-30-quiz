package com.example.quiz.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.service.ifs.UserService;
import com.example.quiz.vo.UserLoginReq;
import com.example.quiz.vo.UserLoginRes;

@RestController
public class UserServiceController {
	
	@Autowired
	public UserService userService;
	
	//JSON format
	@PostMapping(value="api/login")
	public UserLoginRes login(@RequestBody UserLoginReq req, HttpSession session) {
		//已登入成功的帳號，表示資訊已存在session中，可以跳過userService.login的檢查
		String attr = (String)session.getAttribute("account");
		//除了確認session中有資訊外，還確認session與req中的帳號是否依樣
		if(StringUtils.hasText(attr)&&attr.equals(req.getAccount())) {
			return new UserLoginRes(RtnCode.SUCCESSFUL);
		}
		UserLoginRes res = userService.login(req.getAccount(),req.getPwd());
		if(res.getRtncode().getCode()==200) {
			session.setAttribute("account", req.getAccount());
			//設定 session 預設有效時間 : 30分鐘 (單位:秒)
			//下一行，設定session有效時間為300秒
			//session.setMaxInactiveInterval(300);
		}
		return res;
	  //return userService.login(req)
	}
	
	@GetMapping(value="api/logout")
	public UserLoginRes logpot(HttpSession session) {
		//讓session失效
		session.invalidate();
		return new UserLoginRes(RtnCode.SUCCESSFUL);
		}
}