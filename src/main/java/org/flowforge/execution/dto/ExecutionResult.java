package org.flowforge.execution.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExecutionResult {

    private boolean success;

    private Object output;

    private String error;
}