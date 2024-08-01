package com.api.greenlink.auth.repository;

import com.api.greenlink.user.entity.UserGreenlink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<UserGreenlink, Long> {
    Optional<UserDetails> findByUsername(String username);
    boolean existsById(int id);


}
