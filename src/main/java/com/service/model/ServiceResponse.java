package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.service.enums.ResponseStatus;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class ServiceResponse {

    private ResponseInfo responseInfo;
    private List<Service> service;
    private Pagination pagination;

    @Builder(builderMethodName = "fromServiceRequest")
    public static ServiceResponse.ServiceResponseBuilder fromServiceRequest(ServiceRequest
        serviceRequest, ResponseStatus responseStatus){
        return ServiceResponse.builder()
            .responseInfo(ResponseInfo.fromRequestInfo(serviceRequest.getRequestInfo(),responseStatus).build())
            .service(
                Collections.singletonList(serviceRequest.getService()));
    }
}
