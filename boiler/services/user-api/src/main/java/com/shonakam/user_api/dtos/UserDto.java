package com.shonakam.user_api.dtos;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private UUID id;

	@Length(max = 36)
	private String name;
}

// public class User {
// 	private UUID id;
// 	private String name;
// }
