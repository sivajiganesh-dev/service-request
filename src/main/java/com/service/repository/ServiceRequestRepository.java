package com.service.repository;

import com.service.model.AttributeValue;
import com.service.model.Pagination;
import com.service.model.Service;
import com.service.model.ServiceCriteria;
import com.service.model.ServiceSearchRequest;
import com.service.repository.handler.ServiceRowCallbackHandler;
import com.service.utils.PGUtils;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Repository
@Slf4j
public class ServiceRequestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PGUtils pgUtils;

    @Transactional
    public Service create(Service service) {
        try {
            service.setId(UUID.randomUUID().toString());
            service.getAttributes().parallelStream().forEach(a -> {
                a.setId(UUID.randomUUID().toString());
                a.setAuditDetails(service.getAuditDetails());
                a.setServiceId(service.getId());
            });

            String insertQuery = "INSERT INTO service(id,"
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

            jdbcTemplate.update(insertQuery,
                service.getId(),
                service.getTenantId(),
                service.getServiceDefId(),
                service.getReferenceId(),
                service.getAuditDetails().getCreatedBy(),
                service.getAuditDetails().getLastModifiedBy(),
                service.getAuditDetails().getCreatedTime(),
                service.getAuditDetails().getLastModifiedTime(),
                pgUtils.getPGObject(service.getAdditionalDetails()),
                service.getAccountId(),
                service.getClientId());

            insertAttributeValues(service.getAttributes());
            return service;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertAttributeValues(List<AttributeValue> attributeValues) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO attribute_value (id,"
                + " serviceId,"
                + " attributeCode,"
                + " value,"
                + " createdBy,"
                + " lastModifiedBy,"
                + " createdTime,"
                + " lastModifiedTime, "
                + " additionalDetails) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            attributeValues,
            50,
            (PreparedStatement ps, AttributeValue value) -> {
                ps.setString(1, value.getId());
                ps.setString(2, value.getServiceId());
                ps.setString(3, value.getAttributeCode());
                ps.setObject(4, pgUtils.getPGObject(value.getValue()));
                ps.setString(5, value.getAuditDetails().getCreatedBy());
                ps.setString(6, value.getAuditDetails().getLastModifiedBy());
                ps.setLong(7, value.getAuditDetails().getCreatedTime());
                ps.setLong(8, value.getAuditDetails().getLastModifiedTime());
                ps.setObject(9, pgUtils.getPGObject(value.getAdditionalDetails()));
            });
    }

    public List<Service> searchByCriteria(ServiceSearchRequest searchRequest) {
        ServiceCriteria serviceCriteria = searchRequest.getServiceCriteria();
        Pagination pagination = searchRequest.getPagination();

        StringBuilder queryBuilder;
        queryBuilder = new StringBuilder("SELECT s.id service_id, " +
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
            "LEFT JOIN attribute_value sv ON sv.serviceId = s.id WHERE ");

        if (Objects.nonNull(serviceCriteria.getClientId())) {
            queryBuilder
                .append("s.clientId = '")
                .append(serviceCriteria.getClientId())
                .append("' AND ");
        }
        if (Objects.nonNull(serviceCriteria.getTenantId())) {
            queryBuilder
                .append("s.tenantId = '")
                .append(serviceCriteria.getTenantId())
                .append("' AND ");
        }
        if (Objects.nonNull(serviceCriteria.getAccountId())) {
            queryBuilder
                .append("s.accountId = '")
                .append(serviceCriteria.getClientId())
                .append("' AND ");
        }
        if (!CollectionUtils.isEmpty(serviceCriteria.getIds())) {
            queryBuilder
                .append(" s.id in ('")
                .append(String.join("','", serviceCriteria.getIds()))
                .append("') AND ");
        }
        if (!CollectionUtils.isEmpty(serviceCriteria.getServiceDefIds())) {
            queryBuilder
                .append(" s.serviceDefinitionId in ('")
                .append(String.join("','", serviceCriteria.getServiceDefIds()))
                .append("') AND ");
        }
        if (!CollectionUtils.isEmpty(serviceCriteria.getReferenceIds())) {
            queryBuilder
                .append(" s.referenceId in ('")
                .append(String.join("','", serviceCriteria.getReferenceIds()))
                .append("') AND ");
        }
        String query = queryBuilder.toString();
        query = query.substring(0, query.length() - 4);

        query = query + " ORDER BY s.createdtime DESC ";

        if (pagination != null && !(BigDecimal.ZERO.equals(pagination.getLimit())
            || BigDecimal.ZERO.equals(pagination.getOffSet()))) {
            query +=
                " LIMIT " + pagination.getLimit().longValue() + " OFFSET " + pagination.getOffSet()
                    .longValue();
        }

        ServiceRowCallbackHandler serviceRowCallbackHandler = new ServiceRowCallbackHandler();
        jdbcTemplate.query(query, serviceRowCallbackHandler);
        return serviceRowCallbackHandler.getServiceList().isEmpty()
            ? Collections.emptyList()
            : new ArrayList<>(serviceRowCallbackHandler.getServiceList());
    }
}
