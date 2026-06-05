package org.flowforge.execution.service;

import lombok.RequiredArgsConstructor;
import org.flowforge.execution.dto.ExecutionPlan;
import org.flowforge.workflow.graph.DagValidator;
import org.flowforge.workflow.graph.GraphBuilder;
import org.flowforge.workflow.graph.TopologicalSorter;
import org.flowforge.workflow.graph.WorkflowGraph;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExecutionPlannerService {

    private final GraphBuilder graphBuilder;

    private final DagValidator dagValidator;

    private final TopologicalSorter topologicalSorter;

    public ExecutionPlan createPlan(
            UUID workflowId
    ) {

        WorkflowGraph graph =
                graphBuilder.build(workflowId);

        if (!dagValidator.isValidDAG(graph)) {

            throw new RuntimeException(
                    "Workflow contains cycle"
            );
        }

        List<UUID> orderedNodes =
                topologicalSorter.sort(graph);

        return ExecutionPlan.builder()
                .orderedNodes(orderedNodes)
                .build();
    }
}