package org.flowforge.execution.executor;

import lombok.extern.slf4j.Slf4j;
import org.flowforge.common.enums.NodeType;
import org.flowforge.execution.dto.ExecutionContext;
import org.flowforge.execution.dto.ExecutionResult;
import org.flowforge.workflow.entity.WorkflowNode;
import org.springframework.stereotype.Component;

@Slf4j
@Component("DELAY")
public class DelayExecutor implements NodeExecutor {

//    @Override
//    public NodeType getSupportedType() {
//        return NodeType.DELAY;
//    }

    @Override
    public ExecutionResult execute(ExecutionContext node) {

//        WorkflowNode node =
//                node1.workflowNode();

        log.info("Executing Delay Node: {}", node.getWorkflowNode().getName());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info(
                "Current Variables: {}",
                node.getVariables()
                        .getVariables()
        );

        log.info(
                "Config: {}",
                node.getWorkflowNode().getConfig()
        );

        return ExecutionResult.builder()
                .success(true)
                .output("Delay Execution Completed")
                .build();
    }

//    @Override
//    public ExecutionResult execute(ExecutionContext node) {
//        return null;
//    }
}