package com.example.quiz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.quiz.service.ifs.UserService;
import com.example.quiz.vo.UserLoginRes;

@SpringBootTest
public class QuizApplicationTests {
	
	@Autowired
	private UserService userService;

	@Test
	public void userCreateTest() {
		UserLoginRes res = userService.create("A01","A123");
		System.out.println(res.getRtncode().getMessage());
	}
}
