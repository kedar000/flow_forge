package org.flowforge.execution.executor;

import org.flowforge.workflow.entity.WorkflowNode;

public interface NodeExecutor {

    void execute(WorkflowNode node);
}