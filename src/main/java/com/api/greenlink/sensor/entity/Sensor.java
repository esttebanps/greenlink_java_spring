package com.api.greenlink.sensor.entity;

import com.api.greenlink.sensor.enums.TypeSensor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity @Table(name = "sensor")
@AllArgsConstructor @NoArgsConstructor
@Data @Builder
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name es obligatorio")
    @Column(length = 50)
    private String name_sensor;

    //@NotBlank(message = "type es obligatorio")
    @Enumerated(EnumType.STRING)
    private TypeSensor sensor_type;

    @NotBlank(message = "model es obligatorio")
    @Column(length = 50)
    private String model_sensor;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime created_at = LocalDateTime.now();

}
