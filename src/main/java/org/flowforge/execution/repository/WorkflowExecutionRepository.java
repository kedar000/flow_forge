package org.flowforge.execution.repository;

import org.flowforge.execution.entity.WorkflowExecution;
import org.flowforge.workflow.entity.WorkflowNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkflowExecutionRepository
        extends JpaRepository<
                WorkflowExecution,
                UUID> {
    List<WorkflowNode> findAllByWorkflowId(UUID workflowId);
    Optional<WorkflowExecution> findById(UUID id);
}
