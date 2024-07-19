package com.shonakam.user_api.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

// import com.auth0.jwt.JWT;
// import com.auth0.jwt.algorithms.Algorithm;

// import java.util.UUID;
// import java.time.OffsetDateTime;
import org.springframework.stereotype.Service;

import com.shonakam.user_api.dtos.LoginDto;
import com.shonakam.user_api.jwt.JwtService;
import com.shonakam.user_api.models.UserEmail;
import com.shonakam.user_api.models.UserLoginHashedPassword;
import com.shonakam.user_api.repositories.UserEmailRepository;
import com.shonakam.user_api.repositories.UserLoginHashedPasswordRepository;

// import com.shonakam.user_api.config.JwtConfig;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final PasswordEncoder passwordEncoder;
	private final UserEmailRepository emailRepository;
	private final UserLoginHashedPasswordRepository loginHashedRepository;
	private final JwtService _jwtService;
	
	private boolean validatePassword(LoginDto dto) {
		Optional<UserEmail> userEmailOpt = emailRepository.findByEmail(dto.getEmail());

		if (userEmailOpt.isPresent()) {
			UUID userId = userEmailOpt.get().getUser().getId();
			Optional<UserLoginHashedPassword> hashOpt = loginHashedRepository.findByUserId(userId);

			if (hashOpt.isPresent()) {
				if (passwordEncoder.matches(dto.getRaw_password(), hashOpt.get().getHashedPassword())) {
					dto.setId(userId); // ここでidの更新
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String logIn(LoginDto dto) {
		return validatePassword(dto) ? _jwtService.buildJWT(dto) : null;
	}

	@Override
	public void logOut() {

	}
}
