package org.flowforge.execution.executor;

import lombok.extern.slf4j.Slf4j;
import org.flowforge.execution.dto.ExecutionContext;
import org.flowforge.execution.dto.ExecutionResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component("HTTP")
public class HttpExecutor implements NodeExecutor {
    @Override
    public ExecutionResult execute(ExecutionContext node) {

//        WorkflowNode node =
//                node1.workflowNode();

        log.info("Executing HTTP Node: {}", node.workflowNode().getName());


        log.info(
                "Config: {}",
                node.workflowNode().getConfig()
        );

        return ExecutionResult.builder()
                .success(true)
                .output("HTTP Execution Completed")
                .build();
    }


}
