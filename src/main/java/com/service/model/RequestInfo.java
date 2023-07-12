package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "RequestInfo should be used to carry meta information about the requests to the server as described in the fields below. All eGov APIs will use requestinfo as a part of the request body to carry this meta information. Some of this information will be returned back from the server as part of the ResponseInfo in the response body to ensure correlation.")
@Validated
public class RequestInfo {

    @Schema(required = true, description = "unique API ID")
    @NotNull
    @Size(max = 128)
    private String apiId;

    @Schema(required = true, description = "API version - for HTTP based request this will be same as used in path")
    @NotNull
    @Size(max = 32)
    private String ver;

    @Schema(required = true, description = "time in epoch")
    @NotNull
    private Long ts;

    @Schema(required = true, description = "API action to be performed like _create, _update, _search (denoting POST, PUT, GET) or _oauth etc")
    @NotNull
    @Size(max = 32)
    private String action;

    @Schema(description = "Device ID from which the API is called")
    @Size(max = 1024)
    private String did;

    @Schema(description = "API key (API key provided to the caller in case of server to server communication)")
    @Size(max = 256)
    private String key;

    @Schema(required = true, description = "Unique request message id from the caller")
    @NotNull
    @Size(max = 256)
    private String msgId;

    @Schema(description = "UserId of the user calling")
    @Size(max = 256)
    private String requesterId;

    @Schema(description = "//session/jwt/saml token/oauth token - the usual value that would go into HTTP bearer token")
    private String authToken;

    @Schema(description = "User information like name, email, etc.")
    @Valid
    private UserInfo userInfo;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Correlation id")
    private String correlationId;

}
