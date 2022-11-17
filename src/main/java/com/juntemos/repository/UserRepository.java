package com.juntemos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.juntemos.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	

    Optional<User> findById(@Param("id") Long id);

	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}