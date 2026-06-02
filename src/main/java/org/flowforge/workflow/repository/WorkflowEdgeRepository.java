package org.flowforge.workflow.repository;

import org.flowforge.workflow.entity.WorkflowEdge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkflowEdgeRepository extends JpaRepository<WorkflowEdge, UUID> {
    @Query("""
       SELECT e
       FROM WorkflowEdge e
       WHERE e.sourceNode.workflow.id = :workflowId
       """)
    List<WorkflowEdge> findByWorkflowId(UUID workflowId);}
