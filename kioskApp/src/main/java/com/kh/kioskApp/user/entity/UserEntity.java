package com.kh.kioskApp.user.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_user")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String password;
	
	private String userName;
	
	@Column(unique = true)
	private String email;
	
	private String role;
	
	private String phoneNumber;
	
	@CreatedDate
	private LocalDateTime joinDate;
	
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	public void changePassword(String password) {
		this.password = password;
	}
	
	public void changeEmail(String email) {
		this.email = email;
	}
	
	public void chagePhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void changeRole(String role) {
		this.role = role;
	}
}
