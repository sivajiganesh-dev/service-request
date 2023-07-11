package com.service.repository.handler;

import com.service.model.AttributeValue;
import com.service.model.AuditDetails;
import com.service.model.Service;
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

public class ServiceRowCallbackHandler implements RowCallbackHandler {

    @Getter
    private final Set<Service> serviceList;

    public ServiceRowCallbackHandler() {
        serviceList = new HashSet<>();
    }

    @Override
    public void processRow(ResultSet resultSet) throws SQLException {
        Service service = extractService(resultSet);
        if (Objects.isNull(service.getAttributes())) {
            service.setAttributes(new ArrayList<>());
        }
        service.getAttributes().add(extractAttributeValue(resultSet));
        serviceList.add(service);
    }

    private AttributeValue extractAttributeValue(ResultSet resultSet) throws SQLException {
        AttributeValue attributeValue = AttributeValue.builder()
            .id(resultSet.getString("service_value_id"))
            .attributeCode(resultSet.getString("service_value_attribute_code"))
            .auditDetails(AuditDetails.builder()
                .createdBy(resultSet.getString("service_value_created_by"))
                .lastModifiedBy(resultSet.getString("service_value_last_modified_by"))
                .createdTime(resultSet.getLong("service_value_created_time"))
                .lastModifiedTime(resultSet.getLong("service_value_last_modified_time")).build())
            .build();
        try {
            attributeValue.setAdditionalDetails(
                new JSONParser().parse(resultSet.getString("service_value_additional_details")));
            attributeValue.setValue(
                new JSONParser().parse(resultSet.getString("service_value_value")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return attributeValue;
    }

    private Service extractService(ResultSet resultSet) throws SQLException {
        Service service = Service.builder()
            .id(resultSet.getString("service_id"))
            .serviceDefId(resultSet.getString("service_definition_id"))
            .tenantId(resultSet.getString("tenantId"))
            .accountId(resultSet.getString("accountId"))
            .clientId(resultSet.getString("clientId"))
            .serviceDefId(resultSet.getString("service_definition_id"))
            .referenceId(resultSet.getString("referenceId"))
            .auditDetails(AuditDetails.builder()
                .createdBy(resultSet.getString("service_created_by"))
                .lastModifiedBy(resultSet.getString("service_last_modified_by"))
                .createdTime(resultSet.getLong("service_created_time"))
                .lastModifiedTime(resultSet.getLong("service_last_modified_time")).build())
            .build();
        try {
            service.setAdditionalDetails(
                (new JSONParser().parse(resultSet.getString("service_value_additional_details"))));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return service;
    }
}
