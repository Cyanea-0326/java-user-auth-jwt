package com.shonakam.user_api.jwt;

import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import com.shonakam.user_api.config.JwtConfig;
import com.shonakam.user_api.dtos.LoginDto;

import lombok.AllArgsConstructor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
// import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Service
@AllArgsConstructor
public class JwtService {
	private final RsaKeyProvider rsaKeyProvider;
	private final JwtConfig jwtConfig;

	// JWT_BUILDER
	public String buildJWT(LoginDto dto) {
		Algorithm alg = Algorithm.RSA256(rsaKeyProvider.createPrivateKey());

		String token = JWT.create()
				.withIssuer(jwtConfig.properties().getIssuer())				// トークンの発行者
				.withSubject(dto.getId().toString())						// トークンのサブジェクト（ユーザーID）
				.withExpiresAt(OffsetDateTime.now().plusMinutes(15).toInstant())	// 有効期限
				.withIssuedAt(OffsetDateTime.now().toInstant())								// 発行日時
				.withJWTId(UUID.randomUUID().toString())					// 一意識別子
				.withClaim("email", dto.getEmail())					// カスタムクレーム
				// .withArrayClaim("groups", new String[] { "member", "admin" })		// 配列クレーム
				.sign(alg);													// 署名
		
		return token;
	}

	// JWT_VALIDATOR
	public DecodedJWT verifyToken(String token) {
		Algorithm alg = Algorithm.RSA256(rsaKeyProvider.createPublicKey());

		JWTVerifier verifier = JWT.require(alg)
				.withIssuer(jwtConfig.properties().getIssuer())
				.acceptExpiresAt(5)
				.build();
		try {
			return verifier.verify(token);
		} catch (JWTVerificationException e) {
			System.out.println("JWT verification failed..");
			throw e;
		}
	}
}
