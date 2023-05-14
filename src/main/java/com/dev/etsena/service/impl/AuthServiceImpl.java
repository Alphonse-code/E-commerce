package com.dev.etsena.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.etsena.entity.Role;
import com.dev.etsena.entity.Users;
import com.dev.etsena.repository.RoleRepository;
import com.dev.etsena.repository.UsersRepository;
import com.dev.etsena.service.AuthService;

@Service
@Transactional
public class AuthServiceImpl implements AuthService{

	private UsersRepository usersRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	
	
	/**
	 * @param usersRepository
	 * @param roleRepository
	 * @param passwordEncoder
	 */
	public AuthServiceImpl(UsersRepository usersRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.usersRepository = usersRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Users addNewUser(Users users) {
		
		String pw = users.getPassword();
		users.setPassword(passwordEncoder.encode(pw));
		
		return usersRepository.save(users);
	}

	@Override
	public Role addNewRolle(Role role) {
		
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUsers(String username, String roleName) {
		Users user = usersRepository.findByUsername(username);
		Role role = roleRepository.findByRoleName(roleName);
		user.getRoles().add(role);
		
	}

	@Override
	public Users loadUserByUsername(String usename) {
		
		return usersRepository.findByUsername(usename);
	}

	@Override
	public List<Users> listUsers() {
		
		return usersRepository.findAll();
	}

}
