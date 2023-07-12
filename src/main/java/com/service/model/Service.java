package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
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
@Schema(description = "Hold the Service field details as json object.")
@Validated
public class Service {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier of the record")
    private String id;

    @Schema(example = "pb.amritsar", required = true, description = "Tenant Identifier")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId;

    @Schema(required = true, description = "Service definition id")
    @NotNull
    @Size(min = 2, max = 64)
    private String serviceDefId;

    @Schema(description = "if service is delivered for specific entity then we link this to that entity")
    @Size(min = 2, max = 64)
    private String referenceId;

    @Schema(required = true, description = "Account id of the individual")
    @NotNull
    @Size(min = 2, max = 64)
    private String accountId;

    @Schema(description = "Client Id for service")
    @Size(min = 2, max = 64)
    private String clientId;

    @Schema(required = true, description = "values we can pass if field type is selected as('singlevaluelist','multivaluelist'). ")
    @NotNull
    @Valid
    private List<AttributeValue> attributes = new ArrayList<>();

    @Schema(description = "Audit related information")
    @Valid
    private AuditDetails auditDetails;

    @Schema(description = "Any additional key-value pair to be stored with the service.")
    private Object additionalDetails;

}
