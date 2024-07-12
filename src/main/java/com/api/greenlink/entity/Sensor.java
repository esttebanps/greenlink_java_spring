package com.api.greenlink.entity;

import com.api.greenlink.entity.enums.TypeSensor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity @Table(name = "sensor")
@AllArgsConstructor @NoArgsConstructor
@Data @Builder
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sensor_id;

    @Column(length = 50)
    private String name_sensor;

    @Enumerated(EnumType.STRING)
    private TypeSensor sensor_type;

    @Column(length = 50)
    private String model_sensor;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime created_at = LocalDateTime.now();

}
