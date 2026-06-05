package org.flowforge.execution.executor;

import lombok.extern.slf4j.Slf4j;

import org.flowforge.workflow.entity.WorkflowNode;

import org.springframework.stereotype.Component;

@Slf4j
@Component("LOG")
public class LogExecutor
        implements NodeExecutor {

    @Override
    public void execute(WorkflowNode node) {

        log.info("Executing LOG node: {}",
                node.getName());

        log.info("Node config: {}",
                node.getConfig());
    }
}