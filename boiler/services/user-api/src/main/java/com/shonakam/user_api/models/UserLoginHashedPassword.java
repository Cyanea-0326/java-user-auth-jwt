package com.shonakam.user_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_login_hashed_password")
public class UserLoginHashedPassword {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "hashed_password", columnDefinition = "TEXT", nullable = false)
	private String hashedPassword;

	@OneToOne
	@JoinColumn(name = "user_id", columnDefinition = "UUID", nullable = false, unique = true)
	private User user;
}
