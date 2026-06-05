package org.flowforge.execution.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ExecutionPlan {

    private List<UUID> orderedNodes;
}