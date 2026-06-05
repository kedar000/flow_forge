package org.flowforge.workflow.graph;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TopologicalSorter {

    public List<UUID> sort(WorkflowGraph graph) {

        Map<UUID, List<UUID>> adjacencyList =
                graph.getAdjacencyList();

        Map<UUID, Integer> indegree =
                new HashMap<>();

        for (UUID node : graph.getNodeNames().keySet()) {
            indegree.put(node, 0);
        }

        for (List<UUID> neighbours : adjacencyList.values()) {

            for (UUID neighbour : neighbours) {

                indegree.put(
                        neighbour,
                        indegree.getOrDefault(
                                neighbour,
                                0
                        ) + 1
                );
            }
        }

        Queue<UUID> queue =
                new LinkedList<>();

        indegree.forEach((node, degree) -> {

            if (degree == 0) {
                queue.offer(node);
            }
        });

        List<UUID> result =
                new ArrayList<>();

        while (!queue.isEmpty()) {

            UUID current =
                    queue.poll();

            result.add(current);

            for (UUID neighbour :
                    adjacencyList.getOrDefault(
                            current,
                            Collections.emptyList()
                    )) {

                indegree.put(
                        neighbour,
                        indegree.get(neighbour) - 1
                );

                if (indegree.get(neighbour) == 0) {
                    queue.offer(neighbour);
                }
            }
        }

        return result;
    }
}