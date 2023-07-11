package com.service.controller;

import com.service.model.ServiceRequest;
import com.service.model.ServiceResponse;
import com.service.model.ServiceSearchRequest;
import com.service.service.manager.impl.ServiceRequestManager;
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
@RequestMapping("/service/v1")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestManager serviceRequestManager;

    @PostMapping(path = "/_create",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<ServiceResponse> createServiceRequest(
        @Valid @NotNull @RequestBody ServiceRequest serviceRequest) {
        ServiceResponse serviceResponse = serviceRequestManager.createServiceRequest(
            serviceRequest);
        return new ResponseEntity<>(serviceResponse, HttpStatus.ACCEPTED);
    }

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
