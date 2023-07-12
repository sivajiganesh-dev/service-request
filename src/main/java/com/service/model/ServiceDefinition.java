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
@Schema(description = "Holds the Service Definition details json object.")
@Validated
public class ServiceDefinition {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier of the record")
    @Size(min = 2, max = 64)
    private String id;

    @Schema(example = "pb.amritsar", description = "Tenant Identifier")
    @Size(min = 2, max = 64)
    private String tenantId;

    @Schema(required = true, description = "unique namespaced service code to identify the attribute config for code")
    @NotNull
    @Size(min = 2, max = 64)
    private String code;

    @Schema(description = "If false then consider this as soft deleted record")
    private Boolean isActive = true;

    @Schema(required = true, description = "Attribute definition associated list")
    @NotNull
    @Valid
    private List<AttributeDefinition> attributes = new ArrayList<>();

    @Schema(description = "Audit related information")
    @Valid
    private AuditDetails auditDetails;

    @Schema(description = "Any additional key-value pair to be stored with the service.")
    private Object additionalDetails;

    @Schema(description = "Client Id for service definition")
    @Size(min = 2, max = 64)
    private String clientId;

}
