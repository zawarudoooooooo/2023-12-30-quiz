package com.example.quiz.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.entity.Question;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.QuizGetRes;
import com.example.quiz.vo.QuizRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuizServiceImpl implements QuizService {
	
	//import slf4j(library)
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//字串和物件(類別)間做轉換
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private QuizDao quizDao;
	
//	@Override
//	public QuizRes create(String name, String description, LocalDate startDate, 
//			LocalDate endDate,String questionStr ,boolean published) {
//		if(!StringUtils.hasText(name)||!StringUtils.hasText(description)
//				||startDate==null||endDate==null) {
//		   return new QuizRes(RtnCode.PARAM_ERROR.getCode(),RtnCode.PARAM_ERROR.getMessage());
//		}
		//允許先建立問卷資料，而不同時建立問題
//		if(!CollectionUtils.isEmpty(questionList)) {
//			//check question
//			QuizRes checkResult=checkQuestion(questionList);
//			if(checkResult!=null) {
//				return checkResult;
//			}
//		}
//		if(startDate.isAfter(endDate)) {
//			return new QuizRes(RtnCode.DATE_FORMAT_ERROR.getCode(),RtnCode.DATE_FORMAT_ERROR.getMessage());
//		}
//		try {
//			String str = mapper.writeValueAsString(questionStr);
//			Quiz res = quizDao.save(new Quiz(name,description,startDate,endDate,questionStr,published));
//			return new QuizRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage());
//		} catch (JsonProcessingException e) {
//			return new QuizRes(RtnCode.QUIZ_CREATE_ERROR.getCode(),RtnCode.QUIZ_CREATE_ERROR.getMessage());
//		}
//	}

	@Override
	public QuizRes create(String name, String description, LocalDate startDate, 
			LocalDate endDate,List<Question> questionList ,boolean published) {
		if(!StringUtils.hasText(name)||!StringUtils.hasText(description)
				||startDate==null||endDate==null) {
		   return new QuizRes(RtnCode.PARAM_ERROR.getCode(),RtnCode.PARAM_ERROR.getMessage());
		}
		//允許先建立問卷資料，而不同時建立問題
//		if(!CollectionUtils.isEmpty(questionList)) {
//			//check question
//			QuizRes checkResult=checkQuestion(questionList);
//			if(checkResult!=null) {
//				return checkResult;
//			}
//		}
		if(startDate.isAfter(endDate)) {
			return new QuizRes(RtnCode.DATE_FORMAT_ERROR.getCode(),RtnCode.DATE_FORMAT_ERROR.getMessage());
		}
		try {
			String str = mapper.writeValueAsString(questionList);
			Quiz res = quizDao.save(new Quiz(name,description,startDate,endDate,str,published));
			return new QuizRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage());
		} catch (JsonProcessingException e) {
			return new QuizRes(RtnCode.QUIZ_CREATE_ERROR.getCode(),RtnCode.QUIZ_CREATE_ERROR.getMessage());
		}
	}
	
	private QuizRes checkQuestion(List<Question> questionList) {
		for (Question item : questionList) {
			if(item.getNum()==0||!StringUtils.hasText(item.getTitle())
			        ||!StringUtils.hasText(item.getType())){
				return new QuizRes(RtnCode.QUESTION_PARAM_ERROR.getCode(),RtnCode.QUESTION_PARAM_ERROR.getMessage());
			}
		}
		return null;
    }

	public QuizRes update(int num, String name, String description, LocalDate startDate, LocalDate endDate,
			List<Question> questionList, boolean published) {
		if(num<=0) {
			return new QuizRes(RtnCode.PARAM_ERROR.getCode(),RtnCode.PARAM_ERROR.getMessage());
		}
//		if(!CollectionUtils.isEmpty(questionList)) {
//			//check question
//			QuizRes checkResult=checkQuestion(questionList);
//			if(checkResult!=null) {
//				return checkResult;
//			}
//		}
		Optional<Quiz> op = quizDao.findById(num);
		if(op.isEmpty()) {
			return new QuizRes(RtnCode.QUIZ_NOT_FOUND.getCode(),RtnCode.QUIZ_NOT_FOUND.getMessage());
		}
		Quiz quiz=op.get();
		if(quiz.isPublished()&&LocalDate.now().isAfter(quiz.getStartDate())) {
			return new QuizRes(RtnCode.QUIZ_CANNOT_BE_UPDATED.getCode(),RtnCode.QUIZ_CANNOT_BE_UPDATED.getMessage());
		}
	    if(StringUtils.hasText(name)) {
	    	quiz.setName(name);
         }
	    if(StringUtils.hasText(description)) {
	    	quiz.setDescription(description);
         }
		if(startDate.isAfter(endDate)) {
			return new QuizRes(RtnCode.DATE_FORMAT_ERROR.getCode(),RtnCode.DATE_FORMAT_ERROR.getMessage());
		}
		if(startDate!=null) {
			quiz.setStartDate(startDate);
		}
		if(endDate!=null) {
			quiz.setEndDate(endDate);
		}
		quiz.setPublished(published);
		try {
			String str = mapper.writeValueAsString(questionList);
			quiz.setQuestionList(str);
			quizDao.save(new Quiz(num,name,description,startDate,endDate,str,published));
		} catch (JsonProcessingException e) {
			return new QuizRes(RtnCode.QUIZ_CREATE_ERROR.getCode(),RtnCode.QUIZ_CREATE_ERROR.getMessage());
		}
		return new QuizRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage());
	 }

	@Override
	public QuizRes deleteQuiz(List<Integer> numList) throws Exception {
		if(CollectionUtils.isEmpty(numList)) {
			return new QuizRes(RtnCode.QUIZ_ID_LIST_IS_EMPTY.getCode(),RtnCode.QUIZ_ID_LIST_IS_EMPTY.getMessage());
		}
		List<Quiz> quizList = quizDao.findAllById(numList);
		for(Quiz item:quizList) {
			if(item.isPublished()&&LocalDate.now().isAfter(item.getStartDate())) {
				return new QuizRes(RtnCode.QUIZ_CANNOT_BE_UPDATED.getCode(),RtnCode.QUIZ_CANNOT_BE_UPDATED.getMessage());
			}
		}
//	   quizDao.deleteByNumIn(numList);
		try {
//	        quizDao.deleteByNumIn(numList);
			quizDao.deleteAllById(numList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e);
		}
		return new QuizRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public QuizRes deleteQuestion(int quizNum, List<Integer> numList) {
		if(quizNum<=0||CollectionUtils.isEmpty(numList)) {
			return new QuizRes(RtnCode.PARAM_ERROR.getCode(),RtnCode.PARAM_ERROR.getMessage());
		}
		Optional<Quiz> op = quizDao.findById(quizNum);
		if(op.isEmpty()) {
			return new QuizRes(RtnCode.QUIZ_NOT_FOUND.getCode(),RtnCode.QUIZ_NOT_FOUND.getMessage());
		}
		Quiz quiz=op.get();
		String questionStr=quiz.getQuestionList();
		try {
			//maper.readValue把字串讀成類別
			List<Question>questionList=mapper.readValue(questionStr,new TypeReference<List<Question>>() {});
			List<Question>retainList=new ArrayList<>();
			int index=1;
			for(Question item : questionList) {
				//numlist為delete清單，假設item編號不存在要被刪除，就加到新清單
				if(!numList.contains(item.getNum())) {
					item.setNum(index);
					retainList.add(item);
					index++;
				}
			}
		    String retainListStr=mapper.writeValueAsString(retainList);
		    quiz.setQuestionList(retainListStr);
			quizDao.save(quiz);
		} catch (Exception e) {
			return new QuizRes(RtnCode.QUESTION_DELETE_ERROR.getCode(),RtnCode.QUESTION_DELETE_ERROR.getMessage());
		}
		return new QuizRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage());
	}
//三個條件都null，包含撈全部
	@Override
	public QuizRes search(String quizName, LocalDate startDate, LocalDate endDate) {
		quizName=!StringUtils.hasText(quizName)?"":quizName;
		startDate=startDate==null?LocalDate.of(1970, 01, 01):startDate;
		endDate=endDate==null?LocalDate.of(2099, 12, 31):endDate;
		List<Quiz> res=quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(quizName,startDate,endDate);
		return new QuizGetRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage(),res);
	}
	
	//撈前端狀態
	@Override
	public QuizGetRes search(String quizName, LocalDate startDate, LocalDate endDate,boolean isLogin) {
		quizName=!StringUtils.hasText(quizName)?"":quizName;
		startDate=startDate==null?LocalDate.of(1970, 01, 01):startDate;
		endDate=endDate==null?LocalDate.of(2099, 12, 31):endDate;
//		List<Quiz> res = new ArrayList<>();
//		if(isLogin) {
//			res=quizDao.findByNameContainingAndStartDateAndEndDate(quizName,startDate,endDate);
//		}else{
//			res=quizDao.findByNameContainingAndStartDateAndEndDateAndPublishedTrue
//					(quizName,startDate,endDate);
//		}
		List<Quiz> res= isLogin? quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(quizName,startDate,endDate)
				:quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue
				(quizName,startDate,endDate);
		return new QuizGetRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage(),res);
	}

	@Override
	public QuizGetRes getAll() {
		return new QuizGetRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage(), quizDao.findAll());
	}

}
