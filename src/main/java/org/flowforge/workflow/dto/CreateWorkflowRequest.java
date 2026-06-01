package org.flowforge.workflow.dto;

import lombok.Data;

@Data
public class CreateWorkflowRequest {

    private String name;

    private String description;
}