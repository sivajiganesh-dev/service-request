package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.ArrayList;
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
public class ServiceDefinition {

    private String id;
    private String tenantId;
    private String code;
    private Boolean isActive = true;
    private List<AttributeDefinition> attributes = new ArrayList<>();
    private AuditDetails auditDetails;
    private Object additionalDetails;
    private String clientId;

}