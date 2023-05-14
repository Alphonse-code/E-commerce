package com.dev.etsena.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.dev.etsena.entity.Role;
import com.dev.etsena.entity.Users;
import com.dev.etsena.request.AddRoleToUserRequest;
import com.dev.etsena.service.AuthService;
import com.dev.etsena.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v01")
@AllArgsConstructor
public class AuthRestController {

	private AuthService authService;

	@GetMapping("/users_list")
	public List<Users> listeUser() {
		return authService.listUsers();
	}

	@PostMapping("/add_user")
	public Users saveUser(@RequestBody Users users) {
		return authService.addNewUser(users);
	}

	@PostMapping("/add_role")
	public Role saveRole(@RequestBody Role role) {
		return authService.addNewRolle(role);
	}

	@PostMapping("/add_role_to_user")
	public void addRoleToUser(@RequestBody AddRoleToUserRequest request) {
		authService.addRoleToUsers(request.getUsername(), request.getRoleName());
	}

	@GetMapping("/refresh_token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String authToken = request.getHeader(JWTUtil.AUTH_HEADER);

		
		if (authToken != null && authToken.startsWith(JWTUtil.PREFIX)) {
			try {
				System.err.println(authToken);

				String refreshToken = authToken.substring(JWTUtil.PREFIX.length());

				Algorithm algo = Algorithm.HMAC256(JWTUtil.SECRET);

				JWTVerifier jwtVerifier = JWT.require(algo).build();
				DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
				String username = decodedJWT.getSubject();
				Users users = authService.loadUserByUsername(username);
				

				String jwtAccessToken = JWT.create().withSubject(users.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 1 * 60 * 1000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles",
								users.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toList()))
						.sign(algo);
				
				Map<String, String> idToken = new HashMap<>();
				idToken.put("access-token", jwtAccessToken);
				idToken.put("refresh-token", refreshToken);

				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), idToken);
				
			} catch (Exception e) {
				response.setHeader("error-message : ", e.getMessage());
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} else {
			throw new RuntimeException("Refresh token required");
		}

	}
	
	@GetMapping("/profile")
	public Users profile(Principal principal) {
		
		return authService.loadUserByUsername(principal.getName());
	}

}
