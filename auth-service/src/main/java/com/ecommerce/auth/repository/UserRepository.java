package com.ecommerce.auth.repository;

import com.ecommerce.auth.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role.name = :roleName")
    Page<User> findByRoleName(@Param("roleName") String roleName, Pageable pageable);

    @Modifying
    @Query("update User u set u.active = ?1, u.updatedAt = ?3, u.updatedBy = ?4 where u.id = ?2")
    int setActive(boolean isActive, UUID id, Instant updatedAt, String updateBy);

    boolean existsByIdAndActiveIsTrue(UUID id);
}
