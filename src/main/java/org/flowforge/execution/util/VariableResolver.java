package org.flowforge.execution.util;

import org.flowforge.execution.dto.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class VariableResolver {

    public Object get(
            ExecutionContext context,
            String key
    ) {

        return context.getVariables()
                .getVariables()
                .get(key);
    }
}