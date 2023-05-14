package com.dev.etsena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.etsena.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{

	Users findByUsername(String username);
	

}
