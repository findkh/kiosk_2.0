package com.kh.kioskApp.user.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.kh.kioskApp.user.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
	private Long id;
	private String password;
	private String userName;
	private String email;
	private String role;
	private String phoneNumber;
	private LocalDateTime joinDate;
	private LocalDateTime modifiedDate;
	
	public Map<String, Object> getDataMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("mid", id);
		map.put("userName", userName);
		map.put("email", email);
		map.put("role", role);
		map.put("phoneNumber", phoneNumber);
		return map;
	}
	
	public UserDTO(UserEntity userEntity) {
		this.id = userEntity.getId();
		this.password = userEntity.getPassword();
		this.userName = userEntity.getUserName(); 
		this.email = userEntity.getEmail();
		this.role = userEntity.getRole();
		this.phoneNumber = userEntity.getPhoneNumber();
		this.joinDate = userEntity.getJoinDate();
		this.modifiedDate = userEntity.getModifiedDate();
	}
}
