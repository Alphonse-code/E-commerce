package com.dev.etsena.utils;

public class JWTUtil {

	public static final String SECRET = "keySecrest";
	public static final String AUTH_HEADER = "Authorization";
	public static final long EXPIRE_ACCESS_TOKEN = 5 * 60 * 1000;
	public static final String PREFIX = "Bearer ";
	public static final long EXPIRE_REFRESH_TOKEN = 15 * 60 * 1000;
	
	public static final int MINIMUM_PASSWORD_LENGTH = 6;
	
	
}
