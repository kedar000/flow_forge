package org.flowforge.workflow.graph;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GraphNode {

    private UUID id;

    private String name;
}