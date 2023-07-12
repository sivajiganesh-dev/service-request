package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.service.enums.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Validated
@Schema(description = "The object will contain all the response values for Service Definition.")
public class ServiceDefinitionResponse {

    @Schema(description = "Response information")
    @Valid
    private ResponseInfo responseInfo;

    @Schema(description = "List of service definitions")
    @Valid
    private List<ServiceDefinition> serviceDefinition;

    @Schema(description = "Pagination information")
    @Valid
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
