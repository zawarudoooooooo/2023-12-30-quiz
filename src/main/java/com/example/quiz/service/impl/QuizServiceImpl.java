package com.example.quiz.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.QuizRes;

@Service
public class QuizServiceImpl implements QuizService {
	
	@Autowired
	private QuizDao quizDao;

	@Override
	public QuizRes create(String name, String description, LocalDate startDate, LocalDate endDate) {
		if(!StringUtils.hasText(name)||!StringUtils.hasText(description)
				||startDate==null||endDate==null) {
		   return new QuizRes(RtnCode.PARAM_ERROR.getCode(),RtnCode.PARAM_ERROR.getMessage());
		}
		if(startDate.isAfter(endDate)) {
			return new QuizRes(RtnCode.DATE_FORMAT_ERROR.getCode(),RtnCode.DATE_FORMAT_ERROR.getMessage());
		}
		//save 新增 更新
		Quiz res = quizDao.save(new Quiz(name,description,startDate,endDate));
		return new QuizRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage());
	}

}
