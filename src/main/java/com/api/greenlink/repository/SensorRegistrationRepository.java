package com.api.greenlink.repository;

import com.api.greenlink.entity.SensorRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRegistrationRepository extends JpaRepository<SensorRegistration, Long> {
}
