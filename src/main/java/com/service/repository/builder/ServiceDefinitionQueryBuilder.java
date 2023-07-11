package com.service.repository.builder;

public class ServiceDefinitionQueryBuilder {

    public static final String INSERT_INTO_SERVICE_DEFINITION =
        "INSERT INTO service_definition(id,"
            + " tenantId,"
            + " code,"
            + " isActive,"
            + " createdBy,"
            + " lastModifiedBy,"
            + " createdTime,"
            + " lastModifiedTime,"
            + " additionalDetails,"
            + " clientId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_INTO_ATTRIBUTE_DEFINITION =
        "INSERT INTO attribute_definition (id,"
            + " serviceDefinitionId,"
            + " tenantId,"
            + " code,"
            + " dataType,"
            + " values,"
            + " isActive,"
            + " required,"
            + " regex,"
            + " \"order\","
            + " createdBy,"
            + " lastModifiedBy,"
            + " createdTime,"
            + " lastModifiedTime) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String BASE_SEARCH_QUERY_SERVICE_DEFINITION = "SELECT " +
        " sd.id service_definition_id, " +
        " sd.tenantId service_definition_tenant_id, " +
        " sd.code service_definition_code, " +
        " sd.isActive service_definition_is_active, " +
        " sd.additionalDetails service_definition_additional_details, " +
        " sd.clientId service_definition_client_id, " +
        " sd.createdBy service_definition_created_by, " +
        " sd.lastModifiedBy service_definition_last_modified_by, " +
        " sd.createdTime service_definition_created_time, " +
        " sd.lastModifiedTime service_definition_last_modified_time, " +
        " ad.id attribute_definition_id, " +
        " ad.tenantId attribute_definition_tenant_id, " +
        " ad.code attribute_definition_code, " +
        " ad.dataType attribute_definition_data_type, " +
        " ad.\"values\" attribute_definition_values, " +
        " ad.isActive attribute_definition_is_active, " +
        " ad.required attribute_definition_required, " +
        " ad.regex attribute_definition_regex, " +
        " ad.\"order\" attribute_definition_order, " +
        " ad.createdBy attribute_definition_created_by, " +
        " ad.lastModifiedBy attribute_definition_last_modified_by, " +
        " ad.createdTime attribute_definition_created_time, " +
        " ad.lastModifiedTime attribute_definition_last_modified_time, " +
        " ad.additionalDetails attribute_definition_additional_details " +
        "FROM service_definition sd " +
        "LEFT JOIN attribute_definition ad ON sd.id = ad.serviceDefinitionId AND sd.tenantId = ad.tenantId WHERE ";
}
