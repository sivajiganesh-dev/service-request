package com.service.controller;

import com.service.error.response.GenericResponse;
import com.service.model.ServiceRequest;
import com.service.model.ServiceResponse;
import com.service.model.ServiceSearchRequest;
import com.service.service.manager.impl.ServiceRequestManager;
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

@Tag(name = "Service", description = "Service management APIs")

@RestController
@RequestMapping("/service/v1")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestManager serviceRequestManager;

    @Operation(
        summary = "Create service entry",
        description = "Create an entry into service.",
        tags = {"create-service", "post"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ServiceResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")})})
    @PostMapping(path = "/_create",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ServiceResponse> createServiceRequest(
        @Valid @NotNull @RequestBody ServiceRequest serviceRequest) {
        ServiceResponse serviceResponse = serviceRequestManager.createServiceRequest(
            serviceRequest);
        return new ResponseEntity<>(serviceResponse, HttpStatus.ACCEPTED);
    }

    @Operation(
        summary = "Search for service",
        description = "Search for service entry by criteria",
        tags = {"search-service", "post"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ServiceResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")})})

    @PostMapping(path = "/_search",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ServiceResponse> searchServiceRequest(
        @Valid @NotNull @RequestBody ServiceSearchRequest serviceSearchRequest) {
        ServiceResponse serviceResponse = serviceRequestManager.searchServiceRequest(
            serviceSearchRequest);
        return new ResponseEntity<>(serviceResponse, HttpStatus.ACCEPTED);
    }
}
