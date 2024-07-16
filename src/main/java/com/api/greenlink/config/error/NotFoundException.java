package com.api.greenlink.config.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends RuntimeException {

    public NotFoundException(String sensorNotFound) {
        super(sensorNotFound);
    }
}
