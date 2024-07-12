package com.api.greenlink.entity;

import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "sensor_registration")
public class SensorRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registration_id;

    private float value;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime created_at = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;
}
