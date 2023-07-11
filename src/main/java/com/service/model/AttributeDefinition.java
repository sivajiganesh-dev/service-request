package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.service.enums.DataType;
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
public class AttributeDefinition {

    private String id;
    private String tenantId;
    private String code;
    private DataType dataType;
    private List<String> values;
    private Boolean isActive;
    private Boolean required;
    private String regEx;
    private String order;
    private AuditDetails auditDetails;
    private Object additionalDetails;
    private String serviceDefinitionId;
}
