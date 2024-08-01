package com.api.greenlink.user.repository;

import com.api.greenlink.user.entity.UserGreenlink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserGreenlink, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
