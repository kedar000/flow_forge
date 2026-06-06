package org.flowforge.execution.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.flowforge.common.enums.ExecutionStatus;
import org.flowforge.execution.dto.ExecutionContext;
import org.flowforge.execution.dto.ExecutionPlan;
import org.flowforge.execution.dto.ExecutionResult;
import org.flowforge.execution.dto.WorkflowVariables;
import org.flowforge.execution.entity.NodeExecution;
import org.flowforge.execution.entity.WorkflowExecution;
import org.flowforge.execution.executor.NodeExecutor;
import org.flowforge.execution.executor.NodeExecutorFactory;
import org.flowforge.execution.repository.NodeExecutionRepository;
import org.flowforge.execution.repository.WorkflowExecutionRepository;
import org.flowforge.workflow.entity.Workflow;
import org.flowforge.workflow.entity.WorkflowNode;
import org.flowforge.workflow.repository.WorkflowNodeRepository;
import org.flowforge.workflow.repository.WorkflowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor

@Service

public class WorkflowExecutionService {

    private final Logger log = LoggerFactory.getLogger(WorkflowExecutionService.class);

    private final ExecutionPlannerService executionPlannerService;

    private final WorkflowNodeRepository workflowNodeRepository;

    private final WorkflowExecutionRepository workflowExecutionRepository;

    private final NodeExecutionRepository nodeExecutionRepository;

    private final NodeExecutorFactory nodeExecutorFactory;

    private final WorkflowRepository workflowRepository;

    private final ObjectMapper objectMapper;

    @Transactional

    public void executeWorkflow(

            UUID workflowId

    ) {

        // create WorkflowExecution and set state to RUNNING
        WorkflowExecution workflowExecution =
                new WorkflowExecution();

        workflowExecution.setStatus(
                ExecutionStatus.RUNNING
        );

        workflowExecution.setStartedAt(
                LocalDateTime.now()
        );

        workflowExecution =
                workflowExecutionRepository.save(
                        workflowExecution
                );

        // Extract workflow
        Workflow workflow =
                workflowRepository.findById(workflowId)
                        .orElseThrow();

        workflowExecution.setWorkflow(workflow);

        // extract order of nodes execution in the current workflow
        ExecutionPlan plan =
                executionPlannerService
                        .createPlan(workflowId);
        log.info(
                "Execution plan generated: {}",
                plan.getOrderedNodes()
        );

        // global variables to store the output of each node in the current workflow
        WorkflowVariables workflowVariables =
                new WorkflowVariables();

        for (UUID nodeId : plan.getOrderedNodes()) {

            WorkflowNode node =
                    workflowNodeRepository
                            .findById(nodeId)
                            .orElseThrow();

            NodeExecution nodeExecution =
                    new NodeExecution();

            // set current node to RUNNING state
            nodeExecution.setWorkflowExecution(
                    workflowExecution
            );

            nodeExecution.setWorkflowNode(node);

            nodeExecution.setStatus(
                    ExecutionStatus.RUNNING
            );

            nodeExecution.setStartedAt(
                    LocalDateTime.now()
            );

            nodeExecution =
                    nodeExecutionRepository.save(
                            nodeExecution
                    );

            // Execute the node
            try {

                NodeExecutor executor =
                        nodeExecutorFactory.getExecutor(
                                node.getType()
                        );


                ExecutionContext context =
                        ExecutionContext.builder()
                                .workflowExecution(workflowExecution)
                                .workflowNode(node)
                                .variables(workflowVariables)
                                .build();

                // Result of the current node
                ExecutionResult result =
                        executor.execute(context);


                // Save the current node output variables to the global workflow variables
                workflowVariables
                        .getVariables()
                        .put(
                                node.getName(),
                                result.getOutput()
                        );

                // Set node status to SUCCESS
                nodeExecution.setStatus(
                        ExecutionStatus.SUCCESS
                );

                nodeExecution.setCompletedAt(
                        LocalDateTime.now()
                );

                // converting map to json string cause result.getoutput is map
                // for now http response is Map and other executor output is string
                nodeExecution.setOutput(

                        objectMapper.writeValueAsString(

                                result.getOutput()

                        )

                );

                nodeExecutionRepository.save(
                        nodeExecution
                );
            }catch (Exception ex) {

                // Set node status to failed
                nodeExecution.setStatus(
                        ExecutionStatus.FAILED
                );

                nodeExecution.setErrorMessage(
                        ex.getMessage()
                );

                nodeExecution.setCompletedAt(
                        LocalDateTime.now()
                );

                nodeExecutionRepository.save(
                        nodeExecution
                );

                workflowExecution.setStatus(
                        ExecutionStatus.FAILED
                );

                workflowExecution.setCompletedAt(
                        LocalDateTime.now()
                );

                workflowExecutionRepository.save(
                        workflowExecution
                );
                 log.info("Error Executing workflow execution: {}", ex.getMessage());
                throw new RuntimeException("Error executing workflow execution", ex);
            }


        }


        // After completing execution of nodes change status of workflow from RUNNING -> SUCCESS
        workflowExecution.setStatus(
                ExecutionStatus.SUCCESS
        );

        workflowExecution.setCompletedAt(
                LocalDateTime.now()
        );

        workflowExecutionRepository.save(
                workflowExecution
        );


        log.info(
                "Workflow execution completed successfully"
        );

    }
}
