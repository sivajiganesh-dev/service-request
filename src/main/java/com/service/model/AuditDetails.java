package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Collection of audit related fields used by most models")
@Validated
public class AuditDetails {

  @Schema(description = "username (preferred) or userid of the user that created the object")
  private String createdBy;

  @Schema(description = "username (preferred) or userid of the user that last modified the object")
  private String lastModifiedBy;

  @Schema(description = "epoch of the time object is created")
  private Long createdTime;

  @Schema(description = "epoch of the time object is last modified")
  private Long lastModifiedTime;

  @Builder(builderMethodName = "fromRequestInfo")
  public static AuditDetails.AuditDetailsBuilder fromRequestInfo(RequestInfo requestInfo) {
    return AuditDetails.builder()
        .createdBy(requestInfo.getUserInfo().getUserName())
        .lastModifiedBy(requestInfo.getUserInfo().getUserName())
        .createdTime(System.currentTimeMillis())
        .lastModifiedTime(System.currentTimeMillis());
  }
}
