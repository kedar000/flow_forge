package org.flowforge.workflow.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.flowforge.auth.entity.User;
import org.flowforge.common.entity.BaseEntity;
import org.flowforge.common.enums.WorkflowStatus;

@Entity
@Table(name = "workflows")
@Getter
@Setter
public class Workflow extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private WorkflowStatus status = WorkflowStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
}