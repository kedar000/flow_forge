package org.flowforge.execution.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.flowforge.common.entity.BaseEntity;
import org.flowforge.common.enums.ExecutionStatus;

import org.flowforge.workflow.entity.Workflow;

import java.time.LocalDateTime;

@Entity
@Table(name = "workflow_executions")
@Getter
@Setter
public class WorkflowExecution extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus status;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;
}