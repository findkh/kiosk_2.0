package com.kh.kioskApp.user.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.kh.kioskApp.user.entity.UserEntity;
import com.kh.kioskApp.user.exception.UserExceptions;

//@SpringBootTest
@DataJpaTest //rollback됨.
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	@Transactional
	@Commit
	public void testDelete() {
		Long id = 97L;
		try {
			Optional<UserEntity> result = userRepository.findById(id);
			UserEntity userEntity = result.orElseThrow();
			System.out.println(userEntity);
			userRepository.delete(userEntity);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	@Commit
	public void testUpdate() {
		Long id = 1L;
		Optional<UserEntity> result = userRepository.findById(id);
		try {
			UserEntity userEntity = result.orElseThrow();
			userEntity.chagePhoneNumber("011-111-1111");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRead2() {
		String email = "admin@test.com";
		String inputPassword = "12345"; // 평문 비밀번호
		
		try {
			Optional<UserEntity> result = userRepository.findByEmail(email);
			
			UserEntity userEntity = result.orElseThrow(UserExceptions.NOT_FOUND::get);
			
			// 비밀번호 확인
			if (passwordEncoder.matches(inputPassword, userEntity.getPassword())) {
				System.out.println(userEntity);
			} else {
				System.out.println("비밀번호가 일치하지 않습니다.");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRead() {
		Long id = 1l;
		Optional<UserEntity> result = userRepository.findById(id);
		try {
			UserEntity userEntity = result.orElseThrow();
			System.out.println(userEntity);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInsert2() {
		UserEntity userEntity = UserEntity.builder()
			.userName("user2")
			.email("user2@test.com")
			.password(passwordEncoder.encode("1234"))
			.role("USER")
			.phoneNumber("010-2222-3333")
			.build();
		
		userRepository.save(userEntity);
		
		System.out.println("사용자 계정이 생성되었습니다: " + userEntity);
	}
	
	@Test
	public void testInsert1() {
		UserEntity userEntity = UserEntity.builder()
			.userName("admin")
			.email("admin@test.com")
			.password(passwordEncoder.encode("1234"))
			.role("ADMIN")
			.phoneNumber("010-2222-3333")
			.build();
		
		userRepository.save(userEntity);
		
		System.out.println("관리자 계정이 생성되었습니다: " + userEntity);
	}
}
