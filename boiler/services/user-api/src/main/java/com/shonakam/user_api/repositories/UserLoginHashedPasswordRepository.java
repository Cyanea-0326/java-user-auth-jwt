package com.shonakam.user_api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shonakam.user_api.models.UserLoginHashedPassword;

@Repository
public interface UserLoginHashedPasswordRepository extends JpaRepository<UserLoginHashedPassword, Long> {

	@Modifying
	@Query("DELETE FROM UserLoginHashedPassword ulhp WHERE ulhp.user.id = :id")
	void deleteByUserId(UUID id);

	Optional<UserLoginHashedPassword> findByUserId(UUID userId);
}
