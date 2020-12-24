/*
 * Copyright 2020 Netflix, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netflix.conductor.contribs.listener.archive;

import com.netflix.conductor.core.listener.WorkflowStatusListener;
import com.netflix.conductor.core.orchestration.ExecutionDAOFacade;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "workflow", name = "status.listener.type", havingValue = "archive")
public class ArchivingWorkflowListenerConfiguration {

    @Bean
    public WorkflowStatusListener getWorkflowStatusListener(ExecutionDAOFacade executionDAOFacade,
        ArchivingWorkflowListenerProperties properties) {
        if (properties.getWorkflowArchivalTTL() > 0) {
            return new ArchivingWithTTLWorkflowStatusListener(executionDAOFacade, properties);
        } else {
            return new ArchivingWorkflowStatusListener(executionDAOFacade);
        }
    }
}
