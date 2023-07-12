package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.service.enums.DataType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Schema(description = "Hold the attribute definition fields details as json object.")
public class AttributeDefinition {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier of the record")
    @Size(min = 2, max = 64)
    private String id;

    @Schema(example = "pb.amritsar", description = "Tenant Identifier")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId;

    @Schema(required = true, description = "unique code of attribute.")
    @NotNull
    @Size(min = 2, max = 64)
    private String code;

    @Schema(required = true, description = "defines the attribute type")
    @NotNull
    private DataType dataType;

    @Schema(description = "values we can pass if field type is selected as('singlevaluelist','multivaluelist'). ")
    private List<String> values;

    @Schema(required = true, description = "if the value is true then the attribute will appear in search")
    @NotNull
    private Boolean isActive;

    @Schema(description = "value will be true if field is mandatory otherwise false.")
    private Boolean required;

    @Schema(description = "Define the regular expression for field data validations")
    @Size(min=2,max=64)   public String getRegEx() {
        return regEx;
    }
    private String regEx;

    @Schema(description = "order of the attributes for FE support")
    private String order;

    @Schema(description = "Audit related information")
    @Valid
    private AuditDetails auditDetails;

    @Schema(description = "Any additional key-value pair to be stored with the service.")
    private Object additionalDetails;

    @Schema(description = "service definition reference id")
    private String serviceDefinitionId;
}
