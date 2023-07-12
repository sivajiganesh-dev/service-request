package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.service.enums.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
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
@Schema(description = "ResponseInfo should be used to carry metadata information about the response from the server. apiId, ver and msgId in ResponseInfo should always correspond to the same values in respective request's RequestInfo.")
@Validated
public class ResponseInfo {

    @Schema(required = true, description = "unique API ID")
    @NotNull
    @Size(max = 128)
    private String apiId;

    @Schema(required = true, description = "API version")
    @NotNull

    @Size(max = 32)
    private String ver;

    @Schema(required = true, description = "response time in epoch")
    @NotNull
    private Long ts;

    @Schema(description = "unique response message id (UUID) - will usually be the correlation id from the server")
    @Size(max = 256)
    private String resMsgId;

    private String msgId;
    @Schema(required = true, description = "status of request processing - to be enhanced in futuer to include INPROGRESS")
    @NotNull
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

    @Schema(description = "unique response message id (UUID) - will usually be the correlation id from the server")

    @Size(max = 256)
    public String getResMsgId() {
        return resMsgId;
    }

    @Schema(description = "message id of the request")

    @Size(max = 256)
    public String getMsgId() {
        return msgId;
    }

}
