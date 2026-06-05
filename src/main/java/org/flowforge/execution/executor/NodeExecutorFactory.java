package org.flowforge.execution.executor;

import org.flowforge.common.enums.NodeType;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NodeExecutorFactory {

    private final Map<String, NodeExecutor> executors;

    public NodeExecutorFactory(
            Map<String, NodeExecutor> executors
    ) {

        this.executors = executors;
    }

    public NodeExecutor getExecutor(NodeType type) {

        NodeExecutor executor =
                executors.get(type.name());

        if (executor == null) {

            throw new RuntimeException(
                    "No executor found for: " + type
            );
        }

        return executor;
    }
}