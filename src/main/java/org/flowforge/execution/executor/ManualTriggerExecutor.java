package org.flowforge.execution.executor;

import lombok.extern.slf4j.Slf4j;

import org.flowforge.execution.dto.ExecutionContext;
import org.flowforge.execution.dto.ExecutionResult;
import org.flowforge.workflow.entity.WorkflowNode;

import org.springframework.stereotype.Component;

@Slf4j
@Component("MANUAL_TRIGGER")
public class ManualTriggerExecutor
        implements NodeExecutor {

    @Override
    public ExecutionResult execute(
            ExecutionContext context
    ) {

        WorkflowNode node =
                context.workflowNode();

        log.info(
                "Executing Manual Trigger: {}",
                node.getName()
        );

        return ExecutionResult.builder()
                .success(true)
                .output("Manual trigger executed")
                .build();
    }
}