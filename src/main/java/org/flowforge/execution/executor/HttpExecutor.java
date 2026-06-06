package org.flowforge.execution.executor;

import lombok.extern.slf4j.Slf4j;
import org.flowforge.execution.dto.ExecutionContext;
import org.flowforge.execution.dto.ExecutionResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("HTTP")
public class HttpExecutor implements NodeExecutor {
    @Override
    public ExecutionResult execute(
            ExecutionContext context
    ) {

        log.info("Executing HTTP Node: {}", context.getWorkflowNode().getName());
        Map<String,Object> response =
                new HashMap<>();

        response.put("name", "Kedar");
        response.put("email", "test@test.com");

        log.info(
                "Current Variables: {}",
                context.getVariables()
                        .getVariables()
        );

        return ExecutionResult.builder()
                .success(true)
                .output(response)
                .build();
    }


}
