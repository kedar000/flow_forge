package org.flowforge.workflow.graph;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class WorkflowGraph {

    private Map<UUID, List<UUID>> adjacencyList;

    private Map<UUID, String> nodeNames;
}