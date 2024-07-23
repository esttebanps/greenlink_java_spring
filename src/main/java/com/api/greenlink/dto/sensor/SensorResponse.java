package com.api.greenlink.dto.sensor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SensorResponse {
    private String name;
    private String type;
    private String model;
    private LocalDateTime created_at;
}
