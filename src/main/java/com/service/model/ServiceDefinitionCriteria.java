package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Schema(description = "The object will contain all the search parameters for Service Definition.")
@Validated
public class ServiceDefinitionCriteria {

    @Schema(example = "pb.amritsar", required = true, description = "Tenant Identifier")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId;

    @Schema(description = "Search by service definition ids")
    private List<String> ids;

    @Schema(description = "Search by service definition code")
    private List<String> code;

    @Schema(description = "Client Id")
    @Size(min = 2, max = 64)
    private String clientId;
}
