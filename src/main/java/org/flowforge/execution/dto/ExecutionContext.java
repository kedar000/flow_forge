package org.flowforge.execution.dto;

import lombok.Builder;
import lombok.Data;

import lombok.Getter;
import lombok.Setter;
import org.flowforge.execution.entity.WorkflowExecution;
import org.flowforge.workflow.entity.WorkflowNode;

@Data
@Builder
@Getter
@Setter
public class ExecutionContext {

    private WorkflowExecution workflowExecution;

    private WorkflowNode workflowNode;

    private WorkflowVariables variables;
}