package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter setter 포함
//@RequiredArgsConstructor //final 붙은거에 대한 생성ㅈ
@NoArgsConstructor
public class Member {
	private int id;
	private String username;
	private String password;
	private String email; //db에서 들고온 값을 변경할일 없기때문에 final
	
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
	
}
