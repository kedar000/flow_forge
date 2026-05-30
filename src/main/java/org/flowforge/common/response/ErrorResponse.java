package org.flowforge.common.response;
//package org.flowforge.common.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private String message;
    private int status;
    private LocalDateTime timestamp;
}