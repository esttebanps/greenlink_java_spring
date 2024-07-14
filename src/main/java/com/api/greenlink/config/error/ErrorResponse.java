package com.api.greenlink.config.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> details;

    public ErrorResponse(String code, String message){
        this.code = code;
        this.message = message;
        this.details = null;
    }
}
