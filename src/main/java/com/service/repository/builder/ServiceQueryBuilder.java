package com.service.repository.builder;

public class ServiceQueryBuilder {

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
            "LEFT JOIN attribute_value sv ON sv.serviceId = s.id WHERE ";
}
