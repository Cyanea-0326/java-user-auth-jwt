package com.shonakam.user_api.models;

import java.time.LocalDateTime;
import java.util.UUID;
// import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// import jakarta.persistence.PrePersist;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "name", columnDefinition = "CHAR(36)", updatable = true, nullable = false)
	private String name;

	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	// エンティティ間のリレーションシップを定義
	// @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// private Set<UserEmail> emails;

	// @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// private UserLoginHashedPassword loginHashedPassword;

	// @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// private UserDetail detail;

	// @PrePersist
	// protected void prePersist() {
	// 	if (Objects.isNull(this.id)) {
	// 		this.id = UUID.randomUUID();
	// 	}
	// 	if (Objects.isNull(this.createdAt)) {
	// 		this.createdAt = LocalDateTime.now();
	// 	}
	// }
}
