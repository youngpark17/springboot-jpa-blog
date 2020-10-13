package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;
//빈 등록
@Configuration //빈 등록
@EnableWebSecurity //필터를 등록해서 controller로 들어오는 요청 필터링
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근하면 권한 및 인증 미리 체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private PrincipalDetailService principalDetailService;
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	@Bean //Ioc 
	public BCryptPasswordEncoder encodePWD() { //암호 해쉬화
		return new BCryptPasswordEncoder(); //return 되는 비밀번호 값을 스프링이 관리
	}
	
	//시큐리티가 대신 로그인하는데, password를 가로채기 하는데
	//해당 password가 뭘로 해시가 됐는지 알아야 
	//같은 해시로 암호화해서 db에있는 해시랑 비교할 수 있음.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
		//얘를 통해서 encode처리를 해서 로그인을 할때 입력된 비밀번호가 db와 일치하는지 검증.
		super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //csrf 토큰 비활성화,, 우리는 ajax요청을 자바스크립트를 통해 걸었기 때문에.....
			.authorizeRequests()
			.antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
			.permitAll()
			.anyRequest()
			.authenticated()
		.and()
			.formLogin()
			.loginPage("/auth/loginForm")
			.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 url 가로채서 대신 로그인, 세션 관리 Userdetailed 타입을 상속받은. 빈에대해
			.defaultSuccessUrl("/"); //정상일 경우 일로옴
		
	}

}
