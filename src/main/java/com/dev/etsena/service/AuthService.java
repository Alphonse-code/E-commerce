package com.dev.etsena.service;

import java.util.List;

import com.dev.etsena.entity.Role;
import com.dev.etsena.entity.Users;

public interface AuthService {

	Users addNewUser(Users users);
	Role addNewRolle(Role role);
	void addRoleToUsers(String username, String roleName);
	Users loadUserByUsername(String usename);
	List<Users> listUsers();
}
