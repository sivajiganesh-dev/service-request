package com.service.service.manager;

import com.service.model.ServiceDefinitionRequest;
import com.service.model.ServiceDefinitionResponse;
import com.service.model.ServiceDefinitionSearchRequest;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IServiceDefinitionManager {

    ServiceDefinitionResponse createServiceDefinition(@NotNull
    ServiceDefinitionRequest serviceDefinitionRequest);

    ServiceDefinitionResponse searchServiceDefinition(@NotNull ServiceDefinitionSearchRequest searchRequest);
}
