package com.shonakam.user_api.dtos;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
	private UUID id;

	@NotNull
	@Email
	@Length(max = 255)
	private String email;

	@NotNull
	@Size(min = 6, max = 100)
	private String raw_password;
}
