package com.example.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Writer;

@Repository
public interface WriterDao extends JpaRepository<Writer, Integer> {
	
	//正序
	public List<Writer> findByQuizNum(int quizNum);
	
	//倒序
	public List<Writer> findByQuizNumOrderByNumDesc(int quizNum);
}
