package com.example.quiz.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.entity.User;
import com.example.quiz.repository.UserDao;
import com.example.quiz.service.ifs.UserService;
import com.example.quiz.vo.UserLoginRes;

//讓 spring boot 託管(service類)
@Service
public class UserServiceImpl implements UserService {
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	//使用被spring boot 託管的東西
	@Autowired
	//service 透過 Dao 操作DB
	//ctrl+shift+o 自動import
	private UserDao userDao;

	@Override
	public UserLoginRes login(String account, String pwd) {
		//參數檢查
		if(!StringUtils.hasText(account)||!StringUtils.hasText(pwd)) {
			return new UserLoginRes(RtnCode.PARAM_ERROR);
		}
//		User res = userDao.findByAccountAndPwd(account, pwd);
//		if(res==null) {
//			return; //失敗
//		}
//	    return; //成功
		
		//檢查帳號是否存在
		//只有findById是被Optional包起來(記得去判斷是否為空)
		Optional<User> op = userDao.findById(account);
		if(op.isEmpty()) {
			return new UserLoginRes(RtnCode.ACCOUNT_NOT_FOUND);
		}
		User user = op.get();
		if(encoder.matches(pwd, user.getPwd())) {
			return new UserLoginRes(RtnCode.ACCOUNT_NOT_FOUND);
		}
		return new UserLoginRes(RtnCode.SUCCESSFUL);
	}

	@Override
	public UserLoginRes create(String account, String pwd) {
		if(!StringUtils.hasText(account)||!StringUtils.hasText(pwd)) {
			return new UserLoginRes(RtnCode.PARAM_ERROR);
		}
		//檢查帳號是否已存在
		if(userDao.existsById(account)) {
			return new UserLoginRes(RtnCode.ACCOUNT_EXISTED);
		}
		//密碼加密
		userDao.save(new User(account,encoder.encode(pwd)));
		return new UserLoginRes(RtnCode.SUCCESSFUL);
	}
}

