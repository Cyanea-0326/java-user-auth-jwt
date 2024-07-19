package com.shonakam.user_api.controllers;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.auth0.jwt.interfaces.DecodedJWT;
// import com.shonakam.user_api.config.JwtConfig;
import com.shonakam.user_api.dtos.LoginDto;
import com.shonakam.user_api.jwt.JwtService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
@AllArgsConstructor
public class CheckController {

    
    // public CheckController(JwtConfig jwtConfig) {
        //     this.jwtConfig = jwtConfig;
        // }
        
    // private final JwtConfig jwtConfig;
    private final JwtService jwt;

    @GetMapping
    public List<String> check() {
        LoginDto dto = new LoginDto(UUID.randomUUID(), "bobybob@holo.com", "HOLOHOLOLENCE");
        String token = jwt.buildJWT(dto);

        DecodedJWT valid;
        try {
            valid = jwt.verifyToken(token);
        } catch (Exception e) {
            // JWTの検証に失敗した場合の処理
            return Arrays.asList("Error", e.getMessage());
        }
        
        // トークンの情報をリストに追加
        List<String> value = Arrays.asList(
            "token: " + token,
            "Issuer: " + valid.getIssuer(),
            "Subject: " + valid.getSubject(),
            "Expires At: " + valid.getExpiresAt(),
            "Issued At: " + valid.getIssuedAt(),
            "JWT ID: " + valid.getId(),
            "Email: " + valid.getClaim("email").asString()
        );

        return value;
    }
}
