package com.example.quiz;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.quiz.vo.Answer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WriteServiceTest {

	@Test
	public void writeStringTest() {
		List<Answer> list = new ArrayList<>();
		List<String> optionList1 = new ArrayList<>();
		optionList1.add("WRYYYYYY");
		list.add(new Answer(1, optionList1));

		List<String> optionList2 = new ArrayList<>();
		optionList2.add(">_<");
		list.add(new Answer(2, optionList2));

		List<String> optionList3 = new ArrayList<>();
		optionList3.add("Q_Q");
		optionList3.add("TAT");
		list.add(new Answer(3, optionList3));

		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(list));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
