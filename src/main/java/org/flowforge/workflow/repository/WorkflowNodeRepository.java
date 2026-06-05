package org.flowforge.workflow.repository;

import org.flowforge.workflow.entity.WorkflowNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface WorkflowNodeRepository
        extends JpaRepository<WorkflowNode, UUID> {

    @Query("""
           SELECT n
           FROM WorkflowNode n
           WHERE n.workflow.id = :workflowId
           AND n.id NOT IN (
                SELECT e.targetNode.id
                FROM WorkflowEdge e
                WHERE e.sourceNode.workflow.id = :workflowId
           )
           """)
    List<WorkflowNode> findStartNodes(UUID workflowId);
}