package org.flowforge.execution.controller;


import lombok.RequiredArgsConstructor;
import org.flowforge.execution.service.WorkflowExecutionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/executions")
@RequiredArgsConstructor
public class ExecutionController {

    private final WorkflowExecutionService
            workflowExecutionService;

    @PostMapping("/workflow/{workflowId}")

    public String executeWorkflow(

            @PathVariable UUID workflowId

    ) {

        workflowExecutionService.executeWorkflow(

                workflowId

        );

        return "Workflow execution started";

    }
}