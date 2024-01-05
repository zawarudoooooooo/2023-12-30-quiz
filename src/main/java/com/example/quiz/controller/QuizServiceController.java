package com.example.quiz.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.DeleteQuizReq;
import com.example.quiz.vo.QuizGetRes;
import com.example.quiz.vo.QuizReq;
import com.example.quiz.vo.QuizRes;
import com.example.quiz.vo.QuizSearchReq;


@RestController
@CrossOrigin
public class QuizServiceController {
	
	@Autowired
	private QuizService quizService;
	
	@PostMapping(value="quiz/create")
	public QuizRes create(@RequestBody QuizReq req) {
		
		return quizService.create(req.getName(), req.getDescription(), req.getStartDate(), 
				req.getEndDate(), req.getQuestionList(), req.isPublished());
	}
	
//	public QuizRes create(@RequestBody QuizReq req, HttpSession session) {
//		String attr = (String)session.getAttribute("account");
//		if(!StringUtils.hasText(attr)) {
//			return new QuizRes(RtnCode.PLEASE_LOGIN_FIRST.getCode(),RtnCode.PLEASE_LOGIN_FIRST.getMessage());
//		}
//		return quizService.create(req.getName(), req.getDescription(), req.getStartDate(), 
//				req.getEndDate(), req.getQuestionList(), req.isPublished());
//	}
	
	//因為有用@RequestParam，api的url會是 quiz/update?quiz_num=數字
	//@RequestParam參數中有定義value，表示他會收到 quiz_num 此字串後面的值，若沒加，預設就是變數名稱num
	//RequestParam 接收參數
	//value  改名
	@PostMapping(value="quiz/update")
	public QuizRes update(@RequestParam(value="quiz_num") int num,@RequestBody QuizReq req) {
        return quizService.update(num,req.getName(), req.getDescription(), req.getStartDate(), 
				req.getEndDate(), req.getQuestionList(), req.isPublished());
	}
	
	@PostMapping(value="quiz/delete")
	public QuizRes deleteQuiz(@RequestParam (value = "quiz_num_list") List<Integer>numList) throws Exception {
		return quizService.deleteQuiz(numList);
	}
	
	
	//因為只有一個請求參數，所以建議使用此方法
	//因為 @RequestParam 的參數是 List，url 會是 quiz/delete_quiz?quiz_num_list=100,200,300
	@PostMapping(value="quiz/delete_quiz")
	public QuizRes deleteQuiz1(@RequestParam(value="quiz_num_list") List<Integer> numList) throws Exception {
		return quizService.deleteQuiz(numList);
	}
	
	//@RequestParam 的參數有多個，url 的參數列要用&串接
	//url 會是quiz/delete_question?quiz_num=編號&question_num_list=編號1,編號2
	@PostMapping(value="quiz/delete_question")
	public QuizRes deleteQuestion(//
			@RequestParam(value="quiz_num") int quizNum,
			@RequestParam(value="question_num_list") List<Integer> numList){
		return quizService.deleteQuestion(quizNum,numList);
    }
	
	//url 有多個參數，quiz/delete_question?quiz_num=編號&question_num_list=編號1,編號2
	//@RequestParam 用 Map接，quiz_num=編號，quiz_num 會是 map 中的key，編號會是 map 中的 value，其餘以此類推
	@SuppressWarnings("unchecked")
	@PostMapping(value="quiz/delete_question_1")
	public QuizRes deleteQuestion_1(@RequestParam Map<String,Object> paramMap){
		int quizNum = (int)paramMap.get("quiz_num");
		List<Integer> numList = (List<Integer>)paramMap.get("question_num_list");
		return quizService.deleteQuestion(quizNum,numList);
    }
	
	@PostMapping(value="quiz/search")
	public QuizGetRes search (@RequestBody QuizSearchReq req) {
		return quizService.search(req.getQuizName(), req.getStartDate(), req.getEndDate(),req.isLogin());
	}
	
	@PostMapping(value="quiz/getAll")
	public QuizGetRes getAll(@RequestBody QuizSearchReq req) {
		return quizService.getAll();
	}
		
	}
	
	
