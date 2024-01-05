package com.example.quiz.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.entity.Question;
import com.example.quiz.entity.Quiz;
import com.example.quiz.entity.Writer;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.repository.WriterDao;
import com.example.quiz.service.ifs.WriterService;
import com.example.quiz.vo.Answer;
import com.example.quiz.vo.Count;
import com.example.quiz.vo.QuizRes;
import com.example.quiz.vo.StatisticsRes;
import com.example.quiz.vo.WriterGetRes;
import com.example.quiz.vo.WriterReq;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WriterServiceImpl implements WriterService {
	
	//字串和物件(類別)間做轉換
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private QuizDao quizDao;
	
	@Autowired
	private WriterDao writerDao;

	@Override
	public QuizRes write(WriterReq req) {
		if(req.getQuizNum()<=0||!StringUtils.hasText(req.getName())) {
			return new QuizRes(RtnCode.PARAM_ERROR.getCode(),RtnCode.PARAM_ERROR.getMessage());
		}
		if(!StringUtils.hasText(req.getAnswer())) {
			return new QuizRes(RtnCode.NO_QUESTION_ANSWER.getCode(),RtnCode.NO_QUESTION_ANSWER.getMessage());
		}
		//把 answer 的字串轉成 answer list
		try {
			//[{"qNum":1,"optionList":["WRYYYYYY"]},{"qNum":2,"optionList":[">_<"]},{"qNum":3,"optionList":["Q_Q","TAT"]}]
			List<Answer> ansList = mapper.readValue(req.getAnswer(), new TypeReference<List<Answer>>(){});
			Optional<Quiz> op = quizDao.findById(req.getQuizNum());
			if(op.isEmpty()) {
				return new QuizRes(RtnCode.QUIZ_NOT_FOUND.getCode(),RtnCode.QUIZ_NOT_FOUND.getMessage());
			}
			Quiz quiz=op.get();
			String questionStr = quiz.getQuestionList();
			List<Question> questionList = mapper.readValue(questionStr, 
					new TypeReference<List<Question>>(){});
			//檢查必填問題是否有答案
			for(Question qu : questionList) { 
				for(Answer item : ansList) { //問題編號1:選項1,選項3
					if(qu.getNum()==item.getqNum()&&
							qu.isNecessary()&&CollectionUtils.isEmpty(item.getOptionList())) {
						return new QuizRes(RtnCode.NO_QUESTION_ANSWER.getCode(),RtnCode.NO_QUESTION_ANSWER.getMessage());
					}
				}
			}
		} catch (Exception e) {
			return new QuizRes(RtnCode.STRING_PARSER_ERROR.getCode(),RtnCode.STRING_PARSER_ERROR.getMessage());
		}
        writerDao.save(new Writer (req));
        return new QuizRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage());
	}
	
	//問卷回饋摳J個方法
	@Override
	public WriterGetRes findByQuizNum(int quizNum) {
		if(quizNum<=0) {
			return new WriterGetRes(RtnCode.PARAM_ERROR.getCode(),RtnCode.PARAM_ERROR.getMessage(),null);
		}
		List<Writer> res = writerDao.findByQuizNumOrderByNumDesc(quizNum);
		return new  WriterGetRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage(),res);
	}

	@Override
	public StatisticsRes count(int quizNum) {
		
		if(quizNum<=0) {
			return new StatisticsRes(RtnCode.PARAM_ERROR.getCode(),RtnCode.PARAM_ERROR.getMessage(),quizNum,null);
		}
		List<Writer> res = writerDao.findByQuizNum(quizNum);
		//產生問題編號與選項 List 的 Map
		//Map相同key值，會後蓋前
		Map<Integer,List<String>> questionOptionMap = new HashMap<>();
		for(Writer item : res) {
			//[{"qNum":1,"optionList":["WRYYYYYY"]},{"qNum":2,"optionList":[">_<"]},{"qNum":3,"optionList":["Q_Q","TAT"]}]
			String answerStr = item.getAnswer();
			try {
				List<Answer> ansList = mapper.readValue(answerStr, new TypeReference<List<Answer>>(){});
				
				for(Answer ans : ansList) {
					if(questionOptionMap.containsKey(ans.getqNum())) {
						//把key對應的value取出
						List<String> listInMap = questionOptionMap.get(ans.getqNum());
						//將ans中的optionlist增加到原本已存在map中的option list
						listInMap.addAll(ans.getOptionList());
						questionOptionMap.put(ans.getqNum(), listInMap);
					}else
					questionOptionMap.put(ans.getqNum(), ans.getOptionList());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//計算每個問題中，每個選項的次數
		List<Count> countList = new ArrayList<>();
		//透過entryset，可以取得map的key和value
		//        問題編號,選項的List
		for(Entry<Integer, List<String>> mapItem : questionOptionMap.entrySet()) {
			Count count = new Count();
			//計算每題每個選項的次數
			//Map<選項,次數>
			Map<String,Integer> optionCountMap = new HashMap<>();
			for(String str : mapItem.getValue()) {
				if(optionCountMap.containsKey(str)) {
					int oldCount = optionCountMap.get(str);
					oldCount++;
					optionCountMap.put(str, oldCount);
				}else {
					optionCountMap.put(str, 1);
				}
			}
			count.setQuestionNum(mapItem.getKey());
			count.setOptionCountMap(optionCountMap);
			countList.add(count);
		}
		return new  StatisticsRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage(),
				quizNum,countList);
	}
	
	private List<String> getOptionList(String valueStr) {
			valueStr = StringUtils.trimAllWhitespace(valueStr);//"選項1選項2選項3" 切空白
			String[] array = valueStr.split(",");//[選項1,選項2,選項3] 以逗號做區隔
			List<String> optionList = new ArrayList<>();
			optionList.addAll(Arrays.asList(array));
			return optionList;
		}
	
	//以前的方法
	public QuizRes write1(WriterReq req) {
		if(req.getQuizNum()<=0||!StringUtils.hasText(req.getName())) {
			return new QuizRes(RtnCode.PARAM_ERROR.getCode(),RtnCode.PARAM_ERROR.getMessage());
		}
		if(!StringUtils.hasText(req.getAnswer())) {
			return new QuizRes(RtnCode.NO_QUESTION_ANSWER.getCode(),RtnCode.NO_QUESTION_ANSWER.getMessage());
		}
		//把answer的字串轉成map
		try {
			Map<String,String> map = mapper.readValue(req.getAnswer(), Map.class);
			Optional<Quiz> op = quizDao.findById(req.getQuizNum());
			if(op.isEmpty()) {
				return new QuizRes(RtnCode.QUIZ_NOT_FOUND.getCode(),RtnCode.QUIZ_NOT_FOUND.getMessage());
			}
			Quiz quiz=op.get();
			String questionStr = quiz.getQuestionList();
			List<Question> questionList = mapper.readValue(questionStr, 
					new TypeReference<List<Question>>(){});
			//檢查必填問題是否有答案
			for(Question qu : questionList) { 
				for(Entry<String, String> item : map.entrySet()) { //問題編號1:選項1,選項3
					if(String.valueOf(qu.getNum()).equals(item.getKey())&&
							qu.isNecessary()&&!StringUtils.hasText(item.getValue())) {
						return new QuizRes(RtnCode.NO_QUESTION_ANSWER.getCode(),RtnCode.NO_QUESTION_ANSWER.getMessage());
					}
				}
			}
		} catch (Exception e) {
			return new QuizRes(RtnCode.STRING_PARSER_ERROR.getCode(),RtnCode.STRING_PARSER_ERROR.getMessage());
		}
        writerDao.save(new Writer (req));
        return new QuizRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage());
	}
	
	//以前的方法
	public StatisticsRes count1(int quizNum) {
		
		if(quizNum<=0) {
			return new StatisticsRes(RtnCode.PARAM_ERROR.getCode(),RtnCode.PARAM_ERROR.getMessage(),quizNum,null);
		}
		List<Writer> res = writerDao.findByQuizNum(quizNum);
		//產生問題編號與選項 List 的 Map
		//Map  相同key值，會後蓋前
		Map<String,List<String>> questionOptionMap = new HashMap<>();
		for(Writer item : res) {
			String answerStr = item.getAnswer();
            try {
            	//  問題編號,選項 --> 選項有多個時，用逗號串接
				Map<String,String> answermap = mapper.readValue(answerStr, Map.class);
				//去空白並且用逗號切分
				for(Entry<String, String> mapItem : answermap.entrySet()) {
					String valueStr = mapItem.getValue(); //"選項1 選項2 選項3"
					valueStr = StringUtils.trimAllWhitespace(valueStr);//"選項1選項2選項3" 切空白
					String[] array = valueStr.split(",");//[選項1,選項2,選項3] 以逗號做區隔
					List<String> optionList = new ArrayList<>();
					optionList.addAll(Arrays.asList(array));
					//將先前所有的回答(選項)從 questionOptionMap 取出
					List<String> listInMap = questionOptionMap.get(mapItem.getKey());
					//第一個進來時， questionOptionMap 沒東西，所以 = null
					//listInMap = null 時，listInMap.addAll(optionList) 會報錯
					if(listInMap==null) {
						listInMap = new ArrayList<>();
					}
					//再把當前的回答 optionList(選項) 加入
					listInMap.addAll(optionList);
					//最後把整個加起來的結果放回到 questionOptionMap
					//Map : 相同的 key，value 會 後蓋前
					questionOptionMap.put(mapItem.getKey(), listInMap);
					//p1--> {Q1=list1-1, Q2=list1-2, Q3=list1-3}
					//p2--> {Q1=list1-1+list2-1, Q2=list1-2+list2-2, Q3=list1-3+list2-3}
				}
			} catch (Exception e) {
				
			}
		}
		return new  StatisticsRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage(),
				quizNum,null);
	}
}