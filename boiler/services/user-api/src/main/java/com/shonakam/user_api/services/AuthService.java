package com.shonakam.user_api.services;

import com.shonakam.user_api.dtos.LoginDto;

public interface AuthService {
	
	public String logIn(LoginDto dto);

	public void logOut();
}
