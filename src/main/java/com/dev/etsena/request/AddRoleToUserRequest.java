package com.dev.etsena.request;

import lombok.Data;

@Data
public class AddRoleToUserRequest {

	private String username;
	private String roleName;
}
