package com.service.repository.handler;

import com.service.enums.DataType;
import com.service.model.AttributeDefinition;
import com.service.model.AuditDetails;
import com.service.model.ServiceDefinition;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.jdbc.core.RowCallbackHandler;

public class ServiceDefinitionRowCallbackHandler implements RowCallbackHandler {

    @Getter
    private final Set<ServiceDefinition> serviceDefinitionSet;

    public ServiceDefinitionRowCallbackHandler() {
        serviceDefinitionSet = new HashSet<>();
    }

    @Override
    public void processRow(ResultSet resultSet) throws SQLException {
        ServiceDefinition serviceDefinition = extractServiceDefinition(resultSet);
        if (Objects.isNull(serviceDefinition.getAttributes())) {
            serviceDefinition.setAttributes(new ArrayList<>());
        }
        serviceDefinition.getAttributes().add(extractAttributeDefinition(resultSet));
        serviceDefinitionSet.add(serviceDefinition);
    }

    private AttributeDefinition extractAttributeDefinition(ResultSet resultSet)
        throws SQLException {
        AttributeDefinition attributeDefinition = AttributeDefinition.builder()
            .id(resultSet.getString("attribute_definition_id"))
            .tenantId(resultSet.getString("attribute_definition_tenant_id"))
            .code(resultSet.getString("attribute_definition_code"))
            .isActive(resultSet.getBoolean("attribute_definition_is_active"))
            .required(resultSet.getBoolean("attribute_definition_required"))
            .auditDetails(AuditDetails.builder()
                .createdBy(resultSet.getString("attribute_definition_created_by"))
                .lastModifiedBy(resultSet.getString("attribute_definition_last_modified_by"))
                .createdTime(resultSet.getLong("attribute_definition_created_time"))
                .lastModifiedTime(resultSet.getLong("attribute_definition_last_modified_time"))
                .build())
            .order(resultSet.getString("attribute_definition_order"))
            .regEx(resultSet.getString("attribute_definition_regex")).build();
        String dataType = resultSet.getString("attribute_definition_data_type");
        if (dataType != null) {
            attributeDefinition.setDataType(DataType.fromValue(dataType));
        }
        return attributeDefinition;
    }

    private ServiceDefinition extractServiceDefinition(ResultSet resultSet) throws SQLException {
        ServiceDefinition serviceDefinition = ServiceDefinition.builder()
            .id(resultSet.getString("service_definition_id"))
            .tenantId(resultSet.getString("service_definition_tenant_id"))
            .code(resultSet.getString("service_definition_code"))
            .clientId(resultSet.getString("service_definition_client_id"))
            .isActive(resultSet.getBoolean("service_definition_is_active"))
            .auditDetails(
                AuditDetails.builder()
                    .createdBy(resultSet.getString("service_definition_created_by"))
                    .lastModifiedBy(resultSet.getString("service_definition_last_modified_by"))
                    .createdTime(resultSet.getLong("service_definition_created_time"))
                    .lastModifiedTime(resultSet.getLong("service_definition_last_modified_time"))
                    .build())
            .build();

        try {
            serviceDefinition.setAdditionalDetails((new JSONParser().parse(
                resultSet.getString("service_definition_additional_details"))));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return serviceDefinition;
    }
}
