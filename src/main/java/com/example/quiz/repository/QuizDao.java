package com.example.quiz.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Quiz;

@Repository
public interface QuizDao extends JpaRepository<Quiz,Integer> {
	
	public int deleteByNumIn(List<Integer> numList);
	
	//containing 模糊搜尋(只對前面欄位有效)(後面參數隨意命名)
	public List<Quiz> findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String name,LocalDate startDate,LocalDate endDate);
	
	public List<Quiz> findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue
	(String name,LocalDate startDate,LocalDate endDate);
	
	//order by desc 排序遞減(倒序)
	public List<Quiz> findByNameContainingAndStartDateAndEndDateOrderByEndDateDesc
	(String name,LocalDate startDate,LocalDate endDate);

	//order by asc 排序遞增(正序)
	public List<Quiz> findByNameContainingAndStartDateAndEndDateOrderByEndDateAsc
	(String name,LocalDate startDate,LocalDate endDate);

	public Quiz save(Quiz quiz);
	
	//orderbynumdesc
	
}
