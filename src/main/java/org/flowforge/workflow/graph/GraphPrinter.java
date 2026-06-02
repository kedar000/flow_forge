package org.flowforge.workflow.graph;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class GraphPrinter {

    public void print(WorkflowGraph graph) {

        log.info("========== WORKFLOW GRAPH ==========");

        Map<UUID, List<UUID>> adjacencyList =
                graph.getAdjacencyList();

        Map<UUID, String> nodeNames =
                graph.getNodeNames();

        adjacencyList.forEach((source, targets) -> {

            String sourceName =
                    nodeNames.get(source);

            for (UUID target : targets) {

                String targetName =
                        nodeNames.get(target);

                log.info("{} → {}",
                        sourceName,
                        targetName);
            }
        });

        log.info("====================================");
    }
}