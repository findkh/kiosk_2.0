package com.kh.kioskApp.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.kioskApp.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
	Optional<UserEntity> findByEmail(String email);

}
