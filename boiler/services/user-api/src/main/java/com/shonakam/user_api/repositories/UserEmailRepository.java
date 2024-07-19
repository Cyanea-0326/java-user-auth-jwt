package com.shonakam.user_api.repositories;

import com.shonakam.user_api.models.UserEmail;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEmailRepository extends JpaRepository<UserEmail, Long> {
	
	@Modifying
	@Query("DELETE FROM UserEmail ue WHERE ue.user.id = :id")
	void deleteByUserId(UUID id);

	Optional<UserEmail> findByEmail(String email);
}
