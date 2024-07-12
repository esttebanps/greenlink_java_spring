package com.api.greenlink.config.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ErrorBase {
    private String code;
    private String message;
}
