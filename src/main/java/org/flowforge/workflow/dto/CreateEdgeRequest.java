package org.flowforge.workflow.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateEdgeRequest {

    private UUID sourceNodeId;

    private UUID targetNodeId;
}