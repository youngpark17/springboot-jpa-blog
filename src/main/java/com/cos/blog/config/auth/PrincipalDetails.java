package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Getter;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고나면 UserDetails 타입의 오브젝트를
//스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.

@Getter
public class PrincipalDetails implements UserDetails {
	private User user; //컴포지션, 객체 품고있음.
	
	public PrincipalDetails(User user) {
		// TODO Auto-generated constructor stub
		this.user=user;
	}

	@Override
	public String getPassword() { 
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	
	//계정이 만료되지 않았는지 리턴한다.
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true; //true 만료안됨.
	}
	
	//계정이 잠겨있지 않았는지 리턴한다. (true 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //비밀번호가 만료되지 않았는지 리턴
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() { //계정이 활성화(사용가능한지)
		// TODO Auto-generated method stub
		return true;
	}
	//계정이 가지고 있는 권한을 리턴한다.
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return "ROLE_"+user.getRole(); //ROLE_USER //스프링 규칙.
			}
		});
		//collectors.add(()->{return "ROLE_"+user.getRole();});
		return collectors;
	}
	
	
}
