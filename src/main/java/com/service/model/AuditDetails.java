package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
public class AuditDetails {

    private String createdBy;
    private String lastModifiedBy;
    private Long createdTime;
    private Long lastModifiedTime;

    @Builder(builderMethodName = "fromRequestInfo")
    public static AuditDetails.AuditDetailsBuilder fromRequestInfo(RequestInfo requestInfo){
        return AuditDetails.builder()
            .createdBy(requestInfo.getUserInfo().getUserName())
            .lastModifiedBy(requestInfo.getUserInfo().getUserName())
            .createdTime(System.currentTimeMillis())
            .lastModifiedTime(System.currentTimeMillis());
    }
}