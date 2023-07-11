package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.service.enums.ResponseStatus;
import java.util.UUID;
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
public class ResponseInfo {

    private String apiId;
    private String ver;
    private Long ts;
    private String resMsgId;
    private String msgId;
    private ResponseStatus status;

    @Builder(builderMethodName = "fromRequestInfo")
    public static ResponseInfo.ResponseInfoBuilder fromRequestInfo(RequestInfo requestInfo,
        ResponseStatus responseStatus) {
        return ResponseInfo.builder()
            .apiId(requestInfo.getApiId())
            .msgId(requestInfo.getMsgId())
            .resMsgId(UUID.randomUUID().toString())
            .ts(requestInfo.getTs())
            .ver(requestInfo.getVer())
            .status(responseStatus);
    }

}
