package org.flowforge.workflow.repository;

import org.flowforge.workflow.entity.WorkflowNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface WorkflowNodeRepository extends JpaRepository<WorkflowNode, UUID> {
}
