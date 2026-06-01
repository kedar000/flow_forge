package org.flowforge.workflow.controller;

import lombok.RequiredArgsConstructor;

import org.flowforge.workflow.dto.CreateEdgeRequest;
import org.flowforge.workflow.dto.CreateNodeRequest;
import org.flowforge.workflow.dto.CreateWorkflowRequest;

import org.flowforge.workflow.entity.Workflow;
import org.flowforge.workflow.entity.WorkflowEdge;
import org.flowforge.workflow.entity.WorkflowNode;

import org.flowforge.workflow.service.WorkflowService;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
public class WorkflowController {

    private final WorkflowService workflowService;

    @PostMapping
    public Workflow createWorkflow(
            @RequestBody CreateWorkflowRequest request
    ) {
        return workflowService.createWorkflow(request);
    }

    @PostMapping("/{workflowId}/nodes")
    public WorkflowNode addNode(
            @PathVariable UUID workflowId,
            @RequestBody CreateNodeRequest request
    ) {
        return workflowService.addNode(workflowId, request);
    }

    @PostMapping("/{workflowId}/edges")
    public WorkflowEdge addEdge(
            @RequestBody CreateEdgeRequest request
    ) {
        return workflowService.addEdge(request);
    }
}