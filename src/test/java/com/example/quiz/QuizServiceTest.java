package com.example.quiz;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.quiz.entity.Question;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

//@SpringBootTest
public class QuizServiceTest {
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private QuizDao quizDao;
	
	@Test
	public void createQuizTest() {
		List<Question> list = Arrays.asList(new Question(1,"test1","single",true,"AAA"),
				new Question(2,"test2","single",false,"QQQ;WWW;EEE"));
		quizService.create("AAA", "BBB", LocalDate.now(), LocalDate.now().plusDays(1), list, false);
	}
	//將 JSON String 轉乘指定物件(Question) List
	@Test
	public void objectMapperTest() {
		ObjectMapper mapper = new ObjectMapper();
		//Quiz quiz = quizDao.findById(3).get();
		//String questionStr = quiz.getQuestionStr();
		String str =  "[{\"num\":1,\"title\":\"test1\",\"type\":\"single\",\"necessary\":true,\"option\":\"AAA\"}"
				+ "{\"num\":2,\"title\":\"test2\",\"type\":\"single\",\"necessary\":false,\"option\":\"QQQ;WWW;EEE\"}]";
		try {
			List<Question> questionList = mapper.readValue(str, 
					new TypeReference<List<Question>>(){});
			for(Question item : questionList) {
				System.out.println(item.getNum());
			}
		} catch (JsonProcessingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//將 JSON String 轉成 List，用 Map 接
	@Test
	public void objectMapperTest1() {
		ObjectMapper mapper = new ObjectMapper();
		String str =  "[{\"num\":1,\"title\":\"test1\",\"type\":\"single\",\"necessary\":true,\"option\":\"AAA\"}"
				+ "{\"num\":2,\"title\":\"test2\",\"type\":\"single\",\"necessary\":false,\"option\":\"QQQ;WWW;EEE\"}]";
		try {
			List<Map<String,Object>> list = mapper.readValue(str, List.class);
			for(Map<String,Object> map:list) {
				for(Entry<String,Object> item : map.entrySet()) {
					System.out.println("key : "+item.getKey());
					System.out.println("value : "+item.getValue());
				}
			}
		}catch(Exception e) {
				
			}
	}
	
	//將 JSON String 轉成 Map
	@Test
	public void objectMapperTest2() {
		ObjectMapper mapper = new ObjectMapper();
		String str = "{\"num\":1,\"title\":\"test1\",\"type\":\"single\",\"necessary\":true,\"option\":\"AAA\"}";
		try {
			Map<String,Object> res = mapper.readValue(str, Map.class);
			for(Entry<String,Object> item : res.entrySet()) {
				System.out.println("key : "+item.getKey());
				System.out.println("value : "+item.getValue());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
