package com.shonakam.user_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shonakam.user_api.dtos.UserDto;
import com.shonakam.user_api.dtos.CreateUserDto;
import com.shonakam.user_api.dtos.UpdateUserDto;
import com.shonakam.user_api.services.UserService;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping(path = UserController.BASE_URL)
@RequiredArgsConstructor
public class UserController {
	public static final String BASE_URL = "/api/v1/user";

	private final UserService _UserService;

	@GetMapping("")
	public ResponseEntity<List<UserDto>> getUsers() {
		return ResponseEntity.ok(new ArrayList<UserDto>(_UserService.getUsers()));
	}
	
	// Read
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable UUID id) {
		return ResponseEntity.ok(_UserService.getUser(id));
	}
	
	// Create
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@Validated @RequestBody CreateUserDto dto) {
		UserDto newUser = _UserService.createUser(dto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newUser.getId())
				.toUri();
		return ResponseEntity.created(location).body(newUser);
	}
	
	// Update
	@PatchMapping("/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable UUID id, @Validated @RequestBody UpdateUserDto dto) {
		_UserService.updateUser(dto, id);
		return ResponseEntity.noContent().build();
	}
	
	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
		_UserService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}

// return ResponseEntity.ok(new ArrayList<UserDto>(Arrays.asList(
// 	new UserDto(UUID.randomUUID(), "user 1"),
// 	new UserDto(UUID.randomUUID(), "user 2"),
// 	new UserDto(UUID.randomUUID(), "user 3"))));
