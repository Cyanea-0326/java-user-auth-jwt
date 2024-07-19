package com.shonakam.user_api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
	@NotNull
	@Length(max = 36)
	private String name;

	@NotNull
    @Email
    @Length(max = 255)
    private String email;

    @NotNull
    @Size(min = 6, max = 100)
    private String raw_password;

	// private String imageUrl;
    // private String bio;
}
