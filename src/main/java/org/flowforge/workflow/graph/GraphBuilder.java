package org.flowforge.workflow.graph;

import lombok.RequiredArgsConstructor;

import org.flowforge.workflow.entity.WorkflowEdge;

import org.flowforge.workflow.repository.WorkflowEdgeRepository;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class GraphBuilder {

    private final WorkflowEdgeRepository workflowEdgeRepository;

    public WorkflowGraph build(UUID workflowId) {

        List<WorkflowEdge> edges =
                workflowEdgeRepository.findByWorkflowId(workflowId);

        Map<UUID, List<UUID>> adjacencyList = new HashMap<>();
        Map<UUID, String> nodeNames = new HashMap<>();


        for (WorkflowEdge edge : edges) {

            UUID source =
                    edge.getSourceNode().getId();

            UUID target =
                    edge.getTargetNode().getId();

            adjacencyList
                    .computeIfAbsent(source,
                            k -> new ArrayList<>())
                    .add(target);

            nodeNames.put(source, edge.getSourceNode().getName());

            nodeNames.put(target, edge.getTargetNode().getName());
        }

        return WorkflowGraph.builder()
                .adjacencyList(adjacencyList)
                .nodeNames(nodeNames)
                .build();
    }
}