package com.shonakam.user_api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shonakam.user_api.models.User;
// import com.shonakam.user_api.models.UserDetail;
import com.shonakam.user_api.models.UserEmail;
import com.shonakam.user_api.models.UserLoginHashedPassword;
import com.shonakam.user_api.dtos.CreateUserDto;
import com.shonakam.user_api.dtos.UpdateUserDto;
import com.shonakam.user_api.dtos.UserDto;
import com.shonakam.user_api.repositories.UserDetailRepository;
import com.shonakam.user_api.repositories.UserEmailRepository;
import com.shonakam.user_api.repositories.UserLoginHashedPasswordRepository;
import com.shonakam.user_api.repositories.UserRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private final PasswordEncoder passwordEncoder;
	private final UserRepository _userRepository;
	private final UserEmailRepository _userEmailRepository;
	private final UserLoginHashedPasswordRepository _userLoginHashedPasswordRepository;
	private final UserDetailRepository _userDetailRepository;
	
	@Override
	public List<UserDto> getUsers() {
		List<User> users = _userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(userItem -> new UserDto(
				userItem.getId(),
				userItem.getName())).toList();
		return userDtos;
	}

	@Override
	public UserDto getUser(UUID id) {
		User user = _findUserById(id);
		return new UserDto(
				user.getId(),
				user.getName());
	}

	@Override
	@Transactional
	public UserDto createUser(CreateUserDto dto) {
		// パスワードのハッシュ化
		String hashedPassword = passwordEncoder.encode(dto.getRaw_password());
	
		// DBモデルのインスタンスを作成
		// 各モデルに値をセット
		User newUser = new User();
		newUser.setName(dto.getName());
		newUser.setId(UUID.randomUUID());
		newUser.setCreatedAt(LocalDateTime.now()); 
		
		UserEmail newEmail = new UserEmail();
		newEmail.setEmail(dto.getEmail());
		newEmail.setUser(newUser);
		newEmail.setId(null);

		UserLoginHashedPassword newUserLoginHashedPassword = new UserLoginHashedPassword();
		newUserLoginHashedPassword.setHashedPassword(hashedPassword);
		newUserLoginHashedPassword.setUser(newUser);
		newUserLoginHashedPassword.setId(null);

		// データの登録
		User savedUser = _userRepository.save(newUser);
		@SuppressWarnings("unused") // リターン値に今は使わないので
		UserEmail savedEmail = _userEmailRepository.save(newEmail);
		@SuppressWarnings("unused")
		UserLoginHashedPassword savedUserLoginHashedPassword = _userLoginHashedPasswordRepository.save(newUserLoginHashedPassword);

		return new UserDto(
			savedUser.getId(),
			savedUser.getName());
	}

	@Override
	public void updateUser(UpdateUserDto dto, UUID id) {
		
	}

	@Override
	@Transactional
	public void deleteUser(UUID id) {
		try {
			try {
				_userDetailRepository.deleteById(id);
			} catch (EmptyResultDataAccessException e) {
				logger.warn("UserDetail with id {} does not exist", id);
			}

			try {
				_userLoginHashedPasswordRepository.deleteByUserId(id);
			} catch (EmptyResultDataAccessException e) {
				logger.warn("UserLoginHashedPassword with userId {} does not exist", id);
			}

			try {
				_userEmailRepository.deleteByUserId(id);
			} catch (EmptyResultDataAccessException e) {
				logger.warn("UserEmail with userId {} does not exist", id);
			}

			try {
				_userRepository.deleteById(id);
			} catch (EmptyResultDataAccessException e) {
				logger.warn("User with id {} does not exist", id);
			}
		} catch (Exception e) {
			logger.error("An error occurred while deleting the user with id {}: {}", id, e.getMessage());
			throw e; // メソッド全体をトランザクションとして扱うため、再スローする
		}
	}

	private User _findUserById(UUID id) {
        Optional<User> result = _userRepository.findById(id);

        if (result.isEmpty()) {
            throw new RuntimeException("Not found with this ID: " + id);
        }

        return result.get();
	}
}
