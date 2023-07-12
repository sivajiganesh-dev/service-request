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
@Schema(description = "This is acting ID token of the authenticated user on the server. Any value provided by the clients will be ignored and actual user based on authtoken will be used on the server.")
@Validated
public class UserInfo {

    @Schema(required = true, description = "Unique Identifier of the tenant to which user primarily belongs")
    @NotNull
    private String tenantId;

    @Schema(description = "User id of the authenticated user. Will be deprecated in future")
    private Integer id;

    @Schema(required = true, description = "Unique user name of the authenticated user")
    @NotNull
    private String userName;

    @Schema(description = "mobile number of the autheticated user")
    private String mobile;

    @Schema(description = "email address of the authenticated user")
    private String email;

    @Schema(required = true, description = "List of all the roles for the primary tenant")
    @NotNull
    @Valid
    private List<Role> primaryrole = new ArrayList<>();

    @Schema(description = "array of additional tenantids authorized for the authenticated user")
    @Valid
    private List<TenantRole> additionalroles;
}
