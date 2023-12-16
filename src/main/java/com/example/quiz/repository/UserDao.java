package com.example.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.User;

//Dao:Data Access Object (資料操作物件)

//讓 spring boot 託管(Repository類)
//Repository:操作DB的方法
@Repository
//<1.要對哪一個entity做操作,2.在entity內下@Id屬性的資料型態>
public interface UserDao extends JpaRepository<User, String> {
	
	//寫沒有預設的方法
	//findBy 根據什麼欄位找資料
	//語法以小駝峰做串接
	public User findByAccountAndPwd(String account,String pwd);
	
	public boolean existsByAccountAndPwd(String account,String pwd);

}
