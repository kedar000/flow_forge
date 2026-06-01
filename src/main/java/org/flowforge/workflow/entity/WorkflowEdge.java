package org.flowforge.workflow.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.flowforge.common.entity.BaseEntity;

@Entity
@Table(name = "workflow_edges")
@Getter
@Setter
public class WorkflowEdge extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_node_id")
    private WorkflowNode sourceNode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_node_id")
    private WorkflowNode targetNode;
}