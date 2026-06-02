package org.flowforge.workflow.graph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DagValidator {


    public boolean isValidDAG(WorkflowGraph graph) {

        GraphPrinter printer = new GraphPrinter();
        printer.print(graph);

        Map<UUID, List<UUID>> adjacencyList =
                graph.getAdjacencyList();

        Set<UUID> visited = new HashSet<>();

        Set<UUID> recursionStack = new HashSet<>();

        for (UUID node : adjacencyList.keySet()) {

            if (hasCycle(
                    node,
                    adjacencyList,
                    visited,
                    recursionStack
            )) {

                return false;
            }
        }

        return true;
    }

    private boolean hasCycle(
            UUID node,
            Map<UUID, List<UUID>> adjacencyList,
            Set<UUID> visited,
            Set<UUID> recursionStack
    ) {

        if (recursionStack.contains(node)) {
            return true;
        }

        if (visited.contains(node)) {
            return false;
        }

        visited.add(node);

        recursionStack.add(node);

        List<UUID> neighbours =
                adjacencyList.getOrDefault(
                        node,
                        Collections.emptyList()
                );

        for (UUID neighbour : neighbours) {

            if (hasCycle(
                    neighbour,
                    adjacencyList,
                    visited,
                    recursionStack
            )) {

                return true;
            }
        }

        recursionStack.remove(node);

        return false;
    }
}