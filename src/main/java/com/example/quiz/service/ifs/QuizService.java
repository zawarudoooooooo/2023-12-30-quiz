package com.example.quiz.service.ifs;

import java.time.LocalDate;
import java.util.List;

import com.example.quiz.entity.Question;
import com.example.quiz.vo.QuizGetRes;
import com.example.quiz.vo.QuizRes;

public interface QuizService {
	
	public QuizRes create(String name,String description,LocalDate startDate,
			LocalDate endDate,List<Question> questionList,boolean published);
	
//	public QuizRes create(String name,String description,LocalDate startDate,
//			LocalDate endDate,String questionStr,boolean published);
	
	public QuizRes update(int num,String name,String description,LocalDate startDate,
			LocalDate endDate,List<Question> questionList,boolean published);
	
	public QuizRes deleteQuiz(List<Integer>numList) throws Exception;
	
	public QuizRes deleteQuestion(int quizNum,List<Integer>numList);
	
	public QuizGetRes getAll();
	
	public QuizRes search(String quizName,LocalDate startDate,LocalDate endDate);

	public QuizGetRes search(String quizName, LocalDate startDate, LocalDate endDate, boolean isLogin);
//
//	QuizRes update(int num, String name, String description, LocalDate startDate, LocalDate endDate,
//			List<Question> questionList);


}
