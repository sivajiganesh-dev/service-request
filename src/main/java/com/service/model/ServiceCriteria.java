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
@Schema(description = "The object will contain all the search parameters for Service .")
@Validated
public class ServiceCriteria {

    @Schema(example = "pb.amritsar", required = true, description = "Tenant Identifier")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId;

    @Schema(description = "Search by service ids")
    private List<String> ids;

    @Schema(description = "Search by service definition ids")
    private List<String> serviceDefIds;

    @Schema(description = "Search by service reference ids")
    private List<String> referenceIds;

    @Schema(description = "Account Id of individual")
    @Size(min = 2, max = 64)
    private String accountId;

    @Schema(description = "Client Id")
    @Size(min = 2, max = 64)
    private String clientId;

    @Schema(description = "Search based on values provided in service")
    private Object value;

}
