package com.kh.kioskApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //엔티티 시간 처리
public class KioskAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(KioskAppApplication.class, args);
	}

}
