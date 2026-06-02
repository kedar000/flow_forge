package org.flowforge.workflow.service;

import lombok.RequiredArgsConstructor;

import org.flowforge.workflow.graph.DagValidator;
import org.flowforge.workflow.graph.GraphBuilder;
import org.flowforge.workflow.graph.WorkflowGraph;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkflowValidationService {

    private final GraphBuilder graphBuilder;

    private final DagValidator dagValidator;

    public void validate(UUID workflowId) {

        WorkflowGraph graph =
                graphBuilder.build(workflowId);

        boolean valid =
                dagValidator.isValidDAG(graph);

        if (!valid) {
            throw new RuntimeException(
                    "Workflow contains cycle"
            );
        }
    }
}