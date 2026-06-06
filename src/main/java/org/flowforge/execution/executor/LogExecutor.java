package org.flowforge.execution.executor;

import lombok.extern.slf4j.Slf4j;

import org.flowforge.execution.dto.ExecutionContext;
import org.flowforge.execution.dto.ExecutionResult;
import org.flowforge.workflow.entity.WorkflowNode;

import org.springframework.stereotype.Component;

@Slf4j
@Component("LOG")
public class LogExecutor
        implements NodeExecutor {

    @Override
    public ExecutionResult execute(
            ExecutionContext context
    ) {

        log.info("Executing Log Node: {}", context.getWorkflowNode().getName());

        log.info(
                "Current Variables: {}",
                context.getVariables()
                        .getVariables()
        );

        return ExecutionResult.builder()
                .success(true)
                .output("logged")
                .build();
    }
}