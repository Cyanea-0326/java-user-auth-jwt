package com.shonakam.user_api.controllers;

// import java.net.URI;

// import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shonakam.user_api.dtos.LoginDto;
import com.shonakam.user_api.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AuthController.BASE_URL)
public class AuthController {
	public static final String BASE_URL = "/api/v1/auth";

	private final AuthService _authService;

	@PostMapping("/login")
	public ResponseEntity<String> logIn(@Validated @RequestBody LoginDto dto, HttpServletResponse response) {
		String token = _authService.logIn(dto);

		if (token != null) {
			Cookie authCookie = new Cookie("AUTH_TOKEN", token);
			authCookie.setHttpOnly(true);	// クライアント側のJavaScriptからのアクセスを禁止
			authCookie.setSecure(true);		// HTTPSのみで送信
			authCookie.setPath("/");			// ドメイン全体で有効
			authCookie.setMaxAge(900);		// 有効期限を15mに設定
			response.addCookie(authCookie);

			// refresh_tokenつけたいね
		
			// HttpHeaders headers = new HttpHeaders();
			// headers.setLocation(URI.create("/home"))
			return ResponseEntity.status(HttpStatus.FOUND) // HTTP 302 Found
					// .headers(headers)
					.build();
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Object> logOut() {
		
		return ResponseEntity.noContent().build();
	}
}
