package org.flowforge.execution.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.flowforge.common.enums.ExecutionStatus;
import org.flowforge.execution.dto.ExecutionContext;
import org.flowforge.execution.dto.ExecutionPlan;
import org.flowforge.execution.dto.ExecutionResult;
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

    @Transactional

    public void executeWorkflow(

            UUID workflowId

    ) {
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

        Workflow workflow =
                workflowRepository.findById(workflowId)
                        .orElseThrow();

        workflowExecution.setWorkflow(workflow);

        ExecutionPlan plan =
                executionPlannerService
                        .createPlan(workflowId);
        log.info(
                "Execution plan generated: {}",
                plan.getOrderedNodes()
        );

        for (UUID nodeId : plan.getOrderedNodes()) {

            WorkflowNode node =
                    workflowNodeRepository
                            .findById(nodeId)
                            .orElseThrow();

            NodeExecution nodeExecution =
                    new NodeExecution();

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

            try {
                NodeExecutor executor =
                        nodeExecutorFactory.getExecutor(
                                node.getType()
                        );
                ExecutionContext context =
                        new ExecutionContext(
                                workflowExecution,
                                node
                        );

                ExecutionResult result =
                        executor.execute(context);

                nodeExecution.setStatus(
                        ExecutionStatus.SUCCESS
                );

                nodeExecution.setCompletedAt(
                        LocalDateTime.now()
                );

                nodeExecution.setOutput(
                        result.getOutput()
                );

                nodeExecutionRepository.save(
                        nodeExecution
                );
            }catch (Exception ex) {
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
}
