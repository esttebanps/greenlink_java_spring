package com.api.greenlink.config.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ErrorResponse {
    private Integer code;
    private String code_name;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> details;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponse(Integer code,
                         String code_name,
                         String message,
                         LocalDateTime timestamp,
                         String path
    ){
        this.code = code;
        this.code_name = code_name;
        this.message = message;
        this.details = null;
        this.timestamp = timestamp;
        this.path = path;
    }
}
