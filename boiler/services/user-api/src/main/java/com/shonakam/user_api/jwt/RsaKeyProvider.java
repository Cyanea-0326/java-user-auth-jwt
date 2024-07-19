package com.shonakam.user_api.jwt;

import java.util.Base64;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.shonakam.user_api.config.JwtConfig;

@Component
// @AllArgsConstructor
public class RsaKeyProvider {
    private final String secretKeyPath;
    private final String publicKeyPath;

	public RsaKeyProvider(JwtConfig jwtConfig) {
		this.secretKeyPath = jwtConfig.properties().getSecretKeyPath().substring("classpath:".length());
		this.publicKeyPath = jwtConfig.properties().getPublicKeyPath().substring("classpath:".length());
	}

	// PRIVATE_KEY
	public RSAPrivateKey createPrivateKey() {
		try {
			Resource resource = new ClassPathResource(secretKeyPath);
			try (InputStream is = resource.getInputStream()) {
				String privateKeyPem = new BufferedReader(new InputStreamReader(is))
						.lines()
						.collect(Collectors.joining(System.lineSeparator()));
				privateKeyPem = privateKeyPem.replace("-----BEGIN PRIVATE KEY-----", "")
											.replace("-----END PRIVATE KEY-----", "")
											.replaceAll("\\s", "");

				byte[] keyBytes = Base64.getDecoder().decode(privateKeyPem);
				PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				return (RSAPrivateKey) keyFactory.generatePrivate(spec);
			}
		} catch (IOException | GeneralSecurityException e) {
			throw new RuntimeException("Failed to load private key", e);
		}
	}

	// PUBLIC_KEY
	public RSAPublicKey createPublicKey() {
		try {
			Resource resource = new ClassPathResource(publicKeyPath);
			try (InputStream is = resource.getInputStream()) {
				String publicKeyPem = new BufferedReader(new InputStreamReader(is))
						.lines()
						.collect(Collectors.joining(System.lineSeparator()));
				publicKeyPem = publicKeyPem.replace("-----BEGIN PUBLIC KEY-----", "")
										.replace("-----END PUBLIC KEY-----", "")
										.replaceAll("\\s", "");
				byte[] keyBytes = Base64.getDecoder().decode(publicKeyPem);
				X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				return (RSAPublicKey) keyFactory.generatePublic(spec);
			}
		} catch (IOException | GeneralSecurityException e) {
			throw new RuntimeException("Failed to load public key", e);
		}
	}
}
