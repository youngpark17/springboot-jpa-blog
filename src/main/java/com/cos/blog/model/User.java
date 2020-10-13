package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 패턴
@Entity //User 클래스를 읽어서 mysql에 테이블 생성
//@DynamicInsert //insert시 null인필드제외ㄴ
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라감.
	private int id; //시퀀스 auto_increment
	
	
	@Column(nullable = false, length=100, unique = true)
	private String username;
	@Column(nullable = false, length=100)	 
	private String password;
	
	@Column(nullable = false, length=50)
	private String email;
	
	//@ColumnDefault("user")
	//db는 roletype이 없다.
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum을 쓰는게 정확
	
	private String oauth; //kakao, google
		
	@CreationTimestamp //시간이 자동입력
	private Timestamp createDate;
	
}
