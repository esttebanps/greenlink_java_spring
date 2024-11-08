package com.api.greenlink.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class DataInput {
    private String temperature;
    private String humidity;
    private String sun;

}
