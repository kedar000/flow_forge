package org.flowforge.workflow.service;

import lombok.RequiredArgsConstructor;

import org.flowforge.workflow.dto.CreateEdgeRequest;
import org.flowforge.workflow.dto.CreateNodeRequest;
import org.flowforge.workflow.dto.CreateWorkflowRequest;

import org.flowforge.workflow.entity.Workflow;
import org.flowforge.workflow.entity.WorkflowEdge;
import org.flowforge.workflow.entity.WorkflowNode;

import org.flowforge.workflow.repository.WorkflowEdgeRepository;
import org.flowforge.workflow.repository.WorkflowNodeRepository;
import org.flowforge.workflow.repository.WorkflowRepository;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowNodeRepository workflowNodeRepository;
    private final WorkflowEdgeRepository workflowEdgeRepository;

    public Workflow createWorkflow(CreateWorkflowRequest request) {

        Workflow workflow = new Workflow();

        workflow.setName(request.getName());
        workflow.setDescription(request.getDescription());

        return workflowRepository.save(workflow);
    }

    public WorkflowNode addNode(
            UUID workflowId,
            CreateNodeRequest request
    ) {

        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new RuntimeException("Workflow not found"));

        WorkflowNode node = new WorkflowNode();

        node.setWorkflow(workflow);

        node.setName(request.getName());

        node.setType(request.getType());

        node.setConfig(request.getConfig());

        node.setPositionX(request.getPositionX());

        node.setPositionY(request.getPositionY());

        return workflowNodeRepository.save(node);
    }

    public WorkflowEdge addEdge(CreateEdgeRequest request) {

        WorkflowNode sourceNode =
                workflowNodeRepository.findById(request.getSourceNodeId())
                        .orElseThrow(() -> new RuntimeException("Source node not found"));

        WorkflowNode targetNode =
                workflowNodeRepository.findById(request.getTargetNodeId())
                        .orElseThrow(() -> new RuntimeException("Target node not found"));

        WorkflowEdge edge = new WorkflowEdge();

        edge.setSourceNode(sourceNode);

        edge.setTargetNode(targetNode);

        return workflowEdgeRepository.save(edge);
    }
}