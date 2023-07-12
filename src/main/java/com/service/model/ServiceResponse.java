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
@Schema(description = "The object will contain all the response values for Service.")
public class ServiceResponse {

    @Schema(description = "Response information")
    @Valid
    private ResponseInfo responseInfo;

    @Schema(description = "List of service definitions")
    @Valid
    private List<Service> service;

    @Schema(description = "Pagination information")
    @Valid
    private Pagination pagination;

    @Builder(builderMethodName = "fromServiceRequest")
    public static ServiceResponse.ServiceResponseBuilder fromServiceRequest(ServiceRequest
        serviceRequest, ResponseStatus responseStatus) {
        return ServiceResponse.builder()
            .responseInfo(
                ResponseInfo.fromRequestInfo(serviceRequest.getRequestInfo(), responseStatus)
                    .build())
            .service(
                Collections.singletonList(serviceRequest.getService()));
    }

    @Builder(builderMethodName = "fromServiceSearchRequest")
    public static ServiceResponse.ServiceResponseBuilder fromServiceSearchRequest(
        ServiceSearchRequest
            searchRequest, List<Service> searchResults, ResponseStatus responseStatus) {
        return ServiceResponse.builder()
            .responseInfo(
                ResponseInfo.fromRequestInfo(searchRequest.getRequestInfo(), responseStatus)
                    .build())
            .service(searchResults)
            .pagination(searchRequest.getPagination());
    }

}
