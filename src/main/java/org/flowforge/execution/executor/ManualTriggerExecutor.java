package org.flowforge.execution.executor;

import lombok.extern.slf4j.Slf4j;

import org.flowforge.workflow.entity.WorkflowNode;

import org.springframework.stereotype.Component;

@Slf4j
@Component("MANUAL_TRIGGER")
public class ManualTriggerExecutor
        implements NodeExecutor {

    @Override
    public void execute(WorkflowNode node) {

        log.info("Manual trigger executed: {}",
                node.getName());
    }
}