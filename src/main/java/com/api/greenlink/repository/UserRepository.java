package com.api.greenlink.repository;

import com.api.greenlink.entity.UserGreenlink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserGreenlink, Long> {
    Optional<UserDetails> findByUsername(String username);

    boolean existsByUsername(String username);
}
