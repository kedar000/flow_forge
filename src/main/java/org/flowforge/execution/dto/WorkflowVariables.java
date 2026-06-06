package org.flowforge.execution.dto;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class WorkflowVariables {

    private Map<String, Object> variables =
            new HashMap<>();
}
