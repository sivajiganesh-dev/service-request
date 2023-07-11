package com.service.service.manager.impl;

import com.service.enums.ResponseStatus;
import com.service.model.AuditDetails;
import com.service.model.Service;
import com.service.model.ServiceRequest;
import com.service.model.ServiceResponse;
import com.service.repository.ServiceRequestRepository;
import com.service.service.manager.IServiceRequestManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServiceRequestManager implements IServiceRequestManager {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Override
    public ServiceResponse createServiceRequest(ServiceRequest serviceRequest) {
        Service service = serviceRequest.getService();
        service.setAuditDetails(
            AuditDetails.fromRequestInfo(serviceRequest.getRequestInfo()).build());
        serviceRequestRepository.create(service);

        return ServiceResponse.fromServiceRequest(serviceRequest, ResponseStatus.SUCCESSFUL)
            .build();
    }
}
