package com.dev.etsena;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dev.etsena.entity.Role;
import com.dev.etsena.entity.Users;
import com.dev.etsena.service.AuthService;

@SpringBootApplication
public class EtsenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtsenaApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	@Bean
	CommandLineRunner start(AuthService authService) {
		return args ->{
			authService.addNewRolle(new Role(null, "USER"));
			authService.addNewRolle(new Role(null, "ADMIN"));
			authService.addNewRolle(new Role(null, "CUSTOMER_MANAGER"));
			authService.addNewRolle(new Role(null, "PRODUCT_MANAGER"));
			authService.addNewRolle(new Role(null, "BILLS_MANAGER"));
			
			authService.addNewUser(new Users(null, "users1", "code123", "users1@gmail.com",new ArrayList<>(),new ArrayList<>()));
			authService.addNewUser(new Users(null, "users2", "code123", "users2@gmail.com",new ArrayList<>(),new ArrayList<>()));
			authService.addNewUser(new Users(null, "users3", "code123", "users3@gmail.com",new ArrayList<>(),new ArrayList<>()));
			authService.addNewUser(new Users(null, "admin", "code123", "admin@gmail.com",new ArrayList<>(),new ArrayList<>()));
			
			authService.addRoleToUsers("users1", "USER");
			authService.addRoleToUsers("admin", "ADMIN");
			authService.addRoleToUsers("admin", "USER");
			authService.addRoleToUsers("users2", "PRODUCT_MANAGER");
			authService.addRoleToUsers("users3", "CUSTOMER_MANAGER");
			
		};
	}*/

}
