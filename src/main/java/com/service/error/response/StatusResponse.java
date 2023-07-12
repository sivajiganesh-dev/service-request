package com.service.error.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse implements Serializable {

    @NotNull
    private int statusCode;
    @Builder.Default
    private String statusMessage = "";
    @Builder.Default
    private Type statusType = Type.SUCCESS;

    public enum Type {
        ERROR, SUCCESS, WARNING
    }
}
