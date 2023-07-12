package com.service.controller;

import com.service.error.response.GenericResponse;
import com.service.model.ServiceDefinitionRequest;
import com.service.model.ServiceDefinitionResponse;
import com.service.model.ServiceDefinitionSearchRequest;
import com.service.service.manager.impl.ServiceDefinitionManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Service Definition", description = "Service definition management APIs")
@RestController
@RequestMapping("/service/definition/v1")
public class ServiceDefinitionController {

    @Autowired
    private ServiceDefinitionManager serviceDefinitionManager;

    @Operation(
        summary = "Create service definition entry",
        description = "Create an entry into service definition.",
        tags = {"create-service-def", "post"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ServiceDefinitionResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")})})
    @PostMapping(path = "/_create",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ServiceDefinitionResponse> createServiceDefinitionRequest(
        @Valid @NotNull @RequestBody ServiceDefinitionRequest serviceDefinitionRequest) {
        ServiceDefinitionResponse response = serviceDefinitionManager.createServiceDefinition(
            serviceDefinitionRequest);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @Operation(
        summary = "Search service definition entry",
        description = "Search for service definition by criteria.",
        tags = {"search-service-def", "post"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ServiceDefinitionResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")})})
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
