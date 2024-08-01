package com.api.greenlink.sensor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SensorInput {
    @NotBlank(message = "name es obligatorio")
    private String name;
    @NotBlank(message = "type es obligatorio")
    private String type;
    @NotBlank(message = "model es obligatorio")
    private String model;
}
