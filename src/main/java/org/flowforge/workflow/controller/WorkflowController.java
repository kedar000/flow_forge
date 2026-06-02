package org.flowforge.workflow.controller;

import lombok.RequiredArgsConstructor;

import org.flowforge.workflow.dto.CreateEdgeRequest;
import org.flowforge.workflow.dto.CreateNodeRequest;
import org.flowforge.workflow.dto.CreateWorkflowRequest;

import org.flowforge.workflow.entity.Workflow;
import org.flowforge.workflow.entity.WorkflowEdge;
import org.flowforge.workflow.entity.WorkflowNode;

import org.flowforge.workflow.service.WorkflowService;

import org.flowforge.workflow.service.WorkflowValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
public class WorkflowController {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowController.class);
    private final WorkflowService workflowService;
    public final WorkflowValidationService workflowValidationService;

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
            @PathVariable UUID workflowId,
            @RequestBody CreateEdgeRequest request
    ) {

        return workflowService.addEdge(
                workflowId,
                request
        );
    }
}