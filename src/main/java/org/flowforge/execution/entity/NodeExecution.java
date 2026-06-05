package org.flowforge.execution.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.flowforge.common.entity.BaseEntity;
import org.flowforge.common.enums.ExecutionStatus;

import org.flowforge.workflow.entity.WorkflowNode;

import java.time.LocalDateTime;

@Entity
@Table(name = "node_executions")
@Getter
@Setter
public class NodeExecution extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_execution_id")
    private WorkflowExecution workflowExecution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_node_id")
    private WorkflowNode workflowNode;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus status;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    @Column(columnDefinition = "TEXT")
    private String output;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;
}