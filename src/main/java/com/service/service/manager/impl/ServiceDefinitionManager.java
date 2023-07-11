package com.service.service.manager.impl;

import com.service.dto.AddressDto;
import com.service.enums.ResponseStatus;
import com.service.facade.ExternaServiceFacade;
import com.service.model.AuditDetails;
import com.service.model.ServiceDefinition;
import com.service.model.ServiceDefinitionRequest;
import com.service.model.ServiceDefinitionResponse;
import com.service.model.ServiceDefinitionSearchRequest;
import com.service.repository.ServiceDefinitionRepository;
import com.service.service.manager.IServiceDefinitionManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServiceDefinitionManager implements IServiceDefinitionManager {

    @Autowired
    private ServiceDefinitionRepository serviceDefinitionRepository;

    @Autowired
    private ExternaServiceFacade externaServiceFacade;

    @Override
    public ServiceDefinitionResponse createServiceDefinition(
        ServiceDefinitionRequest serviceDefinitionRequest) {
        ServiceDefinition serviceDefinition = serviceDefinitionRequest.getServiceDefinition();
        serviceDefinition.setAuditDetails(
            AuditDetails.fromRequestInfo(serviceDefinitionRequest.getRequestInfo()).build());
        AddressDto addressDto = externaServiceFacade.getAddressDetails();
        serviceDefinition.setAdditionalDetails(addressDto);
        serviceDefinitionRepository.create(serviceDefinition);

        return ServiceDefinitionResponse.fromServiceDefinitionRequest(serviceDefinitionRequest,
            ResponseStatus.SUCCESSFUL).build();
    }

    @Override
    public ServiceDefinitionResponse searchServiceDefinition(
        ServiceDefinitionSearchRequest searchRequest) {
        List<ServiceDefinition> serviceDefinitionList = serviceDefinitionRepository.searchByCriteria(
            searchRequest);
        return ServiceDefinitionResponse.fromServiceDefinitionSearchRequest(searchRequest,
            serviceDefinitionList, ResponseStatus.SUCCESSFUL).build();
    }
}
