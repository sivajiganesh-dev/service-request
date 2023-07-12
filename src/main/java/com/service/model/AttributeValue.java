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
@Schema(description = "Hold the attribute details as object.")
@Validated
public class AttributeValue {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier of the record")
    private String id;

    @Schema(required = true, description = "Unique code of attribute from attributedefinition")
    @NotNull
    private String attributeCode;

    @Schema(required = true, description = "values we can pass if field type is selected as('singlevaluelist','multivaluelist'). ")
    @NotNull
    private Object value;

    @Schema(description = "Audit related information")
    @Valid
    private AuditDetails auditDetails;

    @Schema(description = "Any additional key-value pair to be stored with the service.")
    private Object additionalDetails;

    @Schema(description = "Service reference id")
    private String serviceId;
}
