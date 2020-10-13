package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;


public interface BoardRepository extends JpaRepository<Board,Integer> {

}




//jpa Naming쿼리
//select * from user where username=? and password=?
//User findByUsernameAndPassword(String username, String password);
//
//@Query(value="SELECT * FROM user WHERE username=?1 AND password = ?2", nativeQuery = true)
//User login(String username, String password) ;