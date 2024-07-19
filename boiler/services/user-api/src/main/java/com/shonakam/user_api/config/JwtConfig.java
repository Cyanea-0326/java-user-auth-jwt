package com.shonakam.user_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// いみゅーたぶる
@Configuration
public class JwtConfig {

	@Value("${jwt-config.issuer}")
	private String issuer;

	@Value("${jwt-config.secret-key-path}")
	private String secretKeyPath;

	@Value("${jwt-config.public-key-path}")
	private String publicKeyPath;

	@Bean
	public JwtConfigProperties properties() {
		return new JwtConfigProperties(issuer, secretKeyPath, publicKeyPath);
	}

	public static class JwtConfigProperties {
		private final String issuer;
		private final String secretKeyPath;
		private final String publicKeyPath;

		public JwtConfigProperties(String issuer, String secretKeyPath, String publicKeyPath) {
			this.issuer = issuer;
			this.secretKeyPath = secretKeyPath;
			this.publicKeyPath = publicKeyPath;
		}

		public String getIssuer() {
			return issuer;
		}

		public String getSecretKeyPath() {
			return secretKeyPath;
		}

		public String getPublicKeyPath() {
			return publicKeyPath;
		}
	}
}
