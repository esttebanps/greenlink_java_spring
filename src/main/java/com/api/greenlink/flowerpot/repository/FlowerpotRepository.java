package com.api.greenlink.flowerpot.repository;

import com.api.greenlink.flowerpot.dto.FlowerpotDto;
import com.api.greenlink.flowerpot.entity.Flowerpot;
import com.api.greenlink.user.entity.UserGreenlink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerpotRepository extends JpaRepository<Flowerpot, Long> {
    int countByUser(UserDetails user);
    Page<Flowerpot> findAllByUser(UserGreenlink user, Pageable pageable);
}
