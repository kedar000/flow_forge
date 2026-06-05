package org.flowforge.execution.dto;

import org.flowforge.execution.entity.WorkflowExecution;
import org.flowforge.workflow.entity.WorkflowNode;

public record ExecutionContext(
        WorkflowExecution workflowExecution,
        WorkflowNode workflowNode
) {}