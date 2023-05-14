package com.dev.etsena.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dev.etsena.entity.Users;
import com.dev.etsena.service.AuthService;

import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private AuthService authService;
	
	/**
	 * @param authService
	 */
	public UserDetailsServiceImpl(AuthService authService) {
		super();
		this.authService = authService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = authService.loadUserByUsername(username);
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		users.getRoles().forEach(r ->{
			authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
		});
		
		return new User(users.getUsername(), users.getPassword(), authorities);
	}

}
