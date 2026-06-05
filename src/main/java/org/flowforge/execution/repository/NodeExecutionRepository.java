package org.flowforge.execution.repository;

import org.flowforge.execution.entity.NodeExecution;
import org.flowforge.workflow.entity.WorkflowNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NodeExecutionRepository

        extends JpaRepository<

                NodeExecution,

                UUID> {


}
