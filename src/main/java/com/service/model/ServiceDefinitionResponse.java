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
public class ServiceDefinitionResponse {

    private ResponseInfo responseInfo;
    private List<ServiceDefinition> serviceDefinition;
    private Pagination pagination;

    @Builder(builderMethodName = "fromServiceDefinitionRequest")
    public static ServiceDefinitionResponse.ServiceDefinitionResponseBuilder fromServiceDefinitionRequest(
        ServiceDefinitionRequest serviceDefinitionRequest, ResponseStatus responseStatus) {
        return ServiceDefinitionResponse.builder()
            .responseInfo(ResponseInfo.fromRequestInfo(serviceDefinitionRequest.getRequestInfo(),
                responseStatus).build())
            .serviceDefinition(
                Collections.singletonList(serviceDefinitionRequest.getServiceDefinition()));
    }

    @Builder(builderMethodName = "fromServiceDefinitionRequest")
    public static ServiceDefinitionResponse.ServiceDefinitionResponseBuilder fromServiceDefinitionSearchRequest(
        ServiceDefinitionSearchRequest searchRequest, List<ServiceDefinition> searchResults,
        ResponseStatus responseStatus) {
        return ServiceDefinitionResponse.builder()
            .responseInfo(
                ResponseInfo.fromRequestInfo(searchRequest.getRequestInfo(), responseStatus)
                    .build())
            .pagination(searchRequest.getPagination())
            .serviceDefinition(searchResults);
    }
}
