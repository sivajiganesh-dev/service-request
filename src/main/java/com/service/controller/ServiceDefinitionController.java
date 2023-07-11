package com.service.controller;

import com.service.model.ServiceDefinitionRequest;
import com.service.model.ServiceDefinitionResponse;
import com.service.model.ServiceDefinitionSearchRequest;
import com.service.service.manager.impl.ServiceDefinitionManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service/definition/v1")
public class ServiceDefinitionController {

    @Autowired
    private ServiceDefinitionManager serviceDefinitionManager;

    @PostMapping(path = "/_create",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ServiceDefinitionResponse> createServiceDefinitionRequest(
        @Valid @NotNull @RequestBody ServiceDefinitionRequest serviceDefinitionRequest) {
        ServiceDefinitionResponse response = serviceDefinitionManager.createServiceDefinition(
            serviceDefinitionRequest);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/_search",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ServiceDefinitionResponse> searchServiceDefinitionRequest(
        @Valid @NotNull @RequestBody ServiceDefinitionSearchRequest serviceDefinitionSearchRequest) {
        ServiceDefinitionResponse serviceDefinitionResponse = serviceDefinitionManager.searchServiceDefinition(
            serviceDefinitionSearchRequest);
        return new ResponseEntity<>(serviceDefinitionResponse, HttpStatus.ACCEPTED);
    }
}
