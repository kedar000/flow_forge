package org.flowforge.workflow.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.flowforge.auth.entity.User;
import org.flowforge.auth.repository.UserRepository;
import org.flowforge.workflow.dto.CreateEdgeRequest;
import org.flowforge.workflow.dto.CreateNodeRequest;
import org.flowforge.workflow.dto.CreateWorkflowRequest;

import org.flowforge.workflow.entity.Workflow;
import org.flowforge.workflow.entity.WorkflowEdge;
import org.flowforge.workflow.entity.WorkflowNode;

import org.flowforge.workflow.repository.WorkflowEdgeRepository;
import org.flowforge.workflow.repository.WorkflowNodeRepository;
import org.flowforge.workflow.repository.WorkflowRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkflowService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowService.class);

    private final WorkflowRepository workflowRepository;
    private final WorkflowNodeRepository workflowNodeRepository;
    private final WorkflowEdgeRepository workflowEdgeRepository;
    public final UserRepository userRepository;
    public  final WorkflowValidationService workflowValidationService;
    //dont place this here cause this authenctation apply for the class and at the start of application during bean creation there is no authentication so this always return null
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public Workflow createWorkflow(CreateWorkflowRequest request) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        try{
            logger.info("Creating workflow");
            Workflow workflow = new Workflow();

            workflow.setName(request.getName());
            workflow.setDescription(request.getDescription());

            logger.info("Extracting email from security context holder : " + authentication);
            String email = authentication.getName();
            User user = (User) userRepository.findByEmail(email)
                            .orElseThrow(()-> new UsernameNotFoundException("User not found"));


            workflow.setCreatedBy(user);
            logger.info("Workflow created");

            return workflowRepository.save(workflow);
        }catch (Exception e){
            logger.error("Error while creating workflow", e);
            throw  new RuntimeException("Error creating Workflow : " + e);
        }

    }

    public WorkflowNode addNode(
            UUID workflowId,
            CreateNodeRequest request
    ) {

        try {
            logger.info("Adding node to workflow " + workflowId);
            Workflow workflow = workflowRepository.findById(workflowId)
                    .orElseThrow(() -> new RuntimeException("Workflow not found"));

            WorkflowNode node = new WorkflowNode();

            node.setWorkflow(workflow);

            node.setName(request.getName());

            node.setType(request.getType());

            node.setConfig(request.getConfig());

            node.setPositionX(request.getPositionX());

            node.setPositionY(request.getPositionY());
            logger.info("Node "+ node.getName() +" added to workflow"+ node.getWorkflow().getName());

            return workflowNodeRepository.save(node);

        }catch (Exception e){
            logger.error("Error while creating Node", e);
            throw  new RuntimeException("Error while Adding node to  workflow "+ workflowId + " : " + e);
        }

    }

    @Transactional
    public WorkflowEdge addEdge(
            UUID workflowId,
            CreateEdgeRequest request
    ) {

        WorkflowNode sourceNode =
                workflowNodeRepository.findById(
                        request.getSourceNodeId()
                ).orElseThrow(() ->
                        new RuntimeException("Source node not found"));

        WorkflowNode targetNode =
                workflowNodeRepository.findById(
                        request.getTargetNodeId()
                ).orElseThrow(() ->
                        new RuntimeException("Target node not found"));

        WorkflowEdge edge = new WorkflowEdge();

        edge.setSourceNode(sourceNode);

        edge.setTargetNode(targetNode);

        WorkflowEdge savedEdge =
                workflowEdgeRepository.save(edge);

        workflowValidationService.validate(workflowId);

        return savedEdge;
    }
}