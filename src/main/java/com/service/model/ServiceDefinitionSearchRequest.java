package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
@Schema(description = "The object will contain all the search request values for Service Definition.")
public class ServiceDefinitionSearchRequest {

    @Schema(required = true, description = "Request related information")
    @NotNull
    @Valid
    private RequestInfo requestInfo;

    @Schema(description = "Service definition search request information")
    @Valid
    private ServiceDefinitionCriteria serviceDefinitionCriteria;

    @Schema(description = "Pagination details")
    @Valid
    private Pagination pagination;
}
