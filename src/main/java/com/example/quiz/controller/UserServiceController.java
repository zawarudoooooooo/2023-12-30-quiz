package com.example.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz.service.ifs.UserService;
import com.example.quiz.vo.UserLoginReq;
import com.example.quiz.vo.UserLoginRes;

@RestController
public class UserServiceController {
	
	@Autowired
	public UserService userService;
	
	//JSON format
	@GetMapping(value="api/login")
	public UserLoginRes login(@RequestBody UserLoginReq req) {
		return userService.login(req.getAccount(),req.getPwd());
	  //return userService.login(req)
	}
}
