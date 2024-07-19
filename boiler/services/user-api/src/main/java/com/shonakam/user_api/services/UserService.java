package com.shonakam.user_api.services;

import java.util.List;
import java.util.UUID;
import com.shonakam.user_api.dtos.UserDto;
import com.shonakam.user_api.dtos.CreateUserDto;
import com.shonakam.user_api.dtos.UpdateUserDto;

public interface UserService {
	public List<UserDto> getUsers();

	public UserDto getUser(UUID id);

	public UserDto createUser(CreateUserDto dto);

	public void updateUser(UpdateUserDto dto, UUID id);

	public void deleteUser(UUID id);
}
