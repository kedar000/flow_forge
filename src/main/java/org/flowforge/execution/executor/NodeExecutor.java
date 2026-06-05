package org.flowforge.execution.executor;

import org.flowforge.execution.dto.ExecutionContext;
import org.flowforge.execution.dto.ExecutionResult;
import org.flowforge.workflow.entity.WorkflowNode;

public interface NodeExecutor {

    ExecutionResult execute(ExecutionContext node);
}