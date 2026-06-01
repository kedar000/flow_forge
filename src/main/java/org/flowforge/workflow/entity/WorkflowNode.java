package org.flowforge.workflow.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.flowforge.common.entity.BaseEntity;
import org.flowforge.common.enums.NodeType;

@Entity
@Table(name = "workflow_nodes")
@Getter
@Setter
public class WorkflowNode extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;

    @Enumerated(EnumType.STRING)
    private NodeType type;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String config;

    private Integer positionX;

    private Integer positionY;
}