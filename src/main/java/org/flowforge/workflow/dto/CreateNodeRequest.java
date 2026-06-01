package org.flowforge.workflow.dto;

import lombok.Data;

import org.flowforge.common.enums.NodeType;

@Data
public class CreateNodeRequest {

    private String name;

    private NodeType type;

    private String config;

    private Integer positionX;

    private Integer positionY;
}