package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {

	@Autowired //의존성 주입.
	private UserRepository userRepository;
	
	
	//save함수는 id를 전달하지 않으면 insert
	//save함수는 id를 전달했는데 그 값이 있다면 update
	//save함수는 id를 전달했는데 데이터가 없다면 insert
	
	//email, password
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		
		try {
			userRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return "삭제에 실패하였습니다. 해당 아이디는 db에 없습니다.";
		}
		
		return "삭제 되었습니다.";
	}
	
	@Transactional //함수 종료시에 자동 커밋.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : "+id);
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email: "+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		}); //이걸 가져올때 영속화된다.
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		
		//userRepository.save(user); transactional 어노테이션을 달았더니 save안했는데 업데이트가되네? ->더티체킹
		//더티체킹
		
		return user; 
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한 페이지당 두건의 데이터를 리턴
	//page?page=0  이런식.
	@GetMapping("/dummy/user/page")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC)
	Pageable pageable){
		
		Page<User> pagingUser = userRepository.findAll(pageable);
		List<User> users = pagingUser.getContent();
		
		return users;
	}
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("username : "+ user.getUsername());
		System.out.println("password : "+ user.getPassword());
		System.out.println("email : "+user.getEmail());
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
	//http://localhost:8080/blog/dummy/user/3
	//{id} 주소로 파라미터를 전달 받을 수 있음.
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//user 4를 찾으면 내가 데이터베이스에서 못찾아오면 null,이 되게됨. 따라서 optional형에대한 정리.
		//so nullpoint를 방지하기 위해 optional
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id :"+id);
			}
		});
		//요청 :  웹 브라우저
		//user 객체 = 자바 오브젝트
		//변환(웹 브라우저가 이해할 수 있는 데이터) -> json(Gson 라이브러리)
		//스프링부트 = MessageConverter라는 애가 응답시에 자동 작동.
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson라이브러리를 호출해서
		//user 오브젝트를 json으로 변환해서 던져줌.
		return user;
		
	}
}
