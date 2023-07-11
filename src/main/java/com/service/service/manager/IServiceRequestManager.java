package com.service.service.manager;

import com.service.model.ServiceRequest;
import com.service.model.ServiceResponse;
import com.service.model.ServiceSearchRequest;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IServiceRequestManager {
    ServiceResponse createServiceRequest(@NotNull ServiceRequest serviceRequest);

    ServiceResponse searchServiceRequest(@NotNull ServiceSearchRequest searchRequest);
}
