package com.yassirTest.fakeBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FakeBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(FakeBankApplication.class, args);
	}

}
