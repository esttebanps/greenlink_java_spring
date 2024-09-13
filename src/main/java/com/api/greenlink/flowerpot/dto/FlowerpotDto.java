package com.api.greenlink.flowerpot.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlowerpotDto {
    private String macAddress;

    private String name;

    private String redSsid;

    private String redPassword;

}
