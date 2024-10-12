package com.kh.kioskApp.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.kioskApp.user.dto.UserDTO;
import com.kh.kioskApp.user.entity.UserEntity;
import com.kh.kioskApp.user.exception.UserExceptions;
import com.kh.kioskApp.user.exception.UserTaskException;
import com.kh.kioskApp.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스
@Service
@RequiredArgsConstructor
@Log4j2
@Transactional // 트랜잭션 관리
public class UserService {
	private final UserRepository userRepository; // 사용자 정보 조회를 위한 레포지토리
	private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 위한 인코더
	
	// 이메일과 비밀번호로 사용자를 조회하는 메소드
	public UserDTO read(String email, String password) {
		try {
			// 이메일로 사용자 조회
			Optional<UserEntity> result = userRepository.findByEmail(email);
			UserEntity userEntity = result.orElseThrow(() -> UserExceptions.NOT_FOUND.get()); // 사용자 없으면 예외 발생
			
			// 비밀번호 확인
			if (!passwordEncoder.matches(password, userEntity.getPassword())) {
				throw UserExceptions.BAD_CREDENTIALS.get(); // 비밀번호 불일치 시 예외 발생
			}
			
			// 사용자 정보를 DTO로 변환하여 반환
			return new UserDTO(userEntity);
		} catch (UserTaskException e) {
			// 사용자 관련 예외 발생 시 로그 기록 및 예외 전파
			log.error("Error: {} with code {}", e.getMsg(), e.getCode());
			throw e;
		}
	}
	
	// 이메일로 사용자를 조회하여 DTO로 반환하는 메소드
	public UserDTO getByEmail(String email) {
		try {
			// 이메일로 사용자 조회
			Optional<UserEntity> result = userRepository.findByEmail(email);
			UserEntity userEntity = result.orElseThrow(UserExceptions.NOT_FOUND::get); // 사용자 없으면 예외 발생
			return new UserDTO(userEntity); // 사용자 정보를 DTO로 변환하여 반환
		} catch(UserTaskException e) {
			// 사용자 관련 예외 발생 시 로그 기록 및 예외 전파
			log.error("Error: {} with code {}", e.getMsg(), e.getCode());
			throw e;
		}
	}
}
