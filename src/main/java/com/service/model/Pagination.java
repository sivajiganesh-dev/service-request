package com.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.service.enums.SortOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
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
@Schema(description = "Pagination details")
@Validated
public class Pagination {

    @Schema(description = "Limit for total no of records in single search max limit should be defined as environment variable")
    @Valid
    @DecimalMax("100")
    private BigDecimal limit;

    @Schema(description = "offset or page no")
    @Valid
    private BigDecimal offSet;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Total count for a particular criteria")
    @Valid
    private BigDecimal totalCount;

    @Schema(description = "result sorting order")
    private String sortBy;

    @Schema(description = "Sorting order")
    private SortOrder order;
}
