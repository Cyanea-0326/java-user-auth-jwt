package com.shonakam.user_api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shonakam.user_api.models.UserDetail;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, UUID> {
	
}
