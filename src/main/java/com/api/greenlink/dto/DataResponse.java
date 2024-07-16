package com.api.greenlink.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataResponse {
    private String value;
    private LocalDateTime created_at;
    private String sensor_name;
    private String sensor_type;
}
