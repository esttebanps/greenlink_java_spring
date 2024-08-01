package com.api.greenlink.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class ErrorResponse {
    private Integer code;
    private String code_name;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> details;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private String path;

    public ErrorResponse(Integer code,
                         String code_name,
                         String message,
                         String path
    ){
        this.code = code;
        this.code_name = code_name;
        this.message = message;
        this.details = null;
        this.path = path;
    }
}
