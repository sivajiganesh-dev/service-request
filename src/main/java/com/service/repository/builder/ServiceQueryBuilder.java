package com.service.repository.builder;

import com.service.enums.Condition;
import com.service.model.Pagination;
import com.service.model.ServiceCriteria;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServiceQueryBuilder extends BaseQueryBuilder {

    public static final String INSERT_INTO_SERVICE =
        "INSERT INTO service(id,"
            + " tenantId,"
            + " serviceDefinitionId,"
            + " referenceId,"
            + " createdBy,"
            + " lastModifiedBy,"
            + " createdTime,"
            + " lastModifiedTime,"
            + " additionalDetails,"
            + " accountId,"
            + " clientId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_INTO_ATTRIBUTE_VALUE =
        "INSERT INTO attribute_value (id,"
            + " serviceId,"
            + " attributeCode,"
            + " value,"
            + " createdBy,"
            + " lastModifiedBy,"
            + " createdTime,"
            + " lastModifiedTime, "
            + " additionalDetails) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String BASE_SEARCH_QUERY_SERVICE =
        "SELECT s.id service_id, " +
            " s.tenantId, " +
            " s.serviceDefinitionId service_definition_id, " +
            " s.referenceId, " +
            " s.accountId, " +
            " s.clientId, " +
            " s.additionalDetails, " +
            " s.createdBy service_created_by, " +
            " s.lastModifiedBy service_last_modified_by, " +
            " s.createdTime service_created_time, " +
            " s.lastModifiedTime service_last_modified_time, " +
            " sv.id service_value_id, " +
            " sv.attributeCode service_value_attribute_code, " +
            " sv.value service_value_value, " +
            " sv.createdBy service_value_created_by, " +
            " sv.lastModifiedBy service_value_last_modified_by, " +
            " sv.createdTime service_value_created_time, " +
            " sv.lastModifiedTime service_value_last_modified_time, " +
            " sv.serviceId service_value_service_id, " +
            " sv.additionalDetails  service_value_additional_details " +
            "FROM service s  " +
            "LEFT JOIN service_definition sd ON s.serviceDefinitionId = sd.id  " +
            "LEFT JOIN attribute_value sv ON sv.serviceId = s.id ";
    private ServiceCriteria serviceCriteria;
    private Pagination pagination;

    @Override
    public String createSearchCriteriaQuery() {
        StringBuilder queryBuilder = new StringBuilder(BASE_SEARCH_QUERY_SERVICE);
        addToWhereClause(queryBuilder, "s.clientId", this.serviceCriteria.getClientId(),
            Condition.EQ);
        addToWhereClause(queryBuilder, "s.tenantId", this.serviceCriteria.getTenantId(),
            Condition.EQ);
        addToWhereClause(queryBuilder, "s.accountId", this.serviceCriteria.getClientId(),
            Condition.EQ);
        addToWhereClause(queryBuilder, "s.id", this.serviceCriteria.getIds(), Condition.EQ);
        addToWhereClause(queryBuilder, "s.serviceDefinitionId",
            this.serviceCriteria.getServiceDefIds(),
            Condition.IN);
        addToWhereClause(queryBuilder, "s.referenceId", this.serviceCriteria.getReferenceIds(),
            Condition.IN);

        addPagination(queryBuilder, this.pagination, "s.");
        return queryBuilder.toString();
    }
}
