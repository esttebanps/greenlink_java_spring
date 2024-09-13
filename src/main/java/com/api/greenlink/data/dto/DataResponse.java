package com.api.greenlink.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataResponse {
    private String temperature;
    private String humidity;
    private String sun;
    private Instant createDate;
}
