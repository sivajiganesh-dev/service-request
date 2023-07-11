package com.service.service.manager;

import com.service.model.ServiceRequest;
import com.service.model.ServiceResponse;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IServiceRequestManager {
    ServiceResponse createServiceRequest(@NotNull ServiceRequest serviceRequest);
}
