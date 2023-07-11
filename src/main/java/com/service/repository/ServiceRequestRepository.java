package com.service.repository;

import com.service.model.AttributeValue;
import com.service.model.Service;
import com.service.utils.PGUtils;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    /*private void search(ServiceDefinitionSearchRequest searchRequest) {
        ServiceDefinitionCriteria criteria = searchRequest.getServiceDefinitionCriteria();
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        if (CollectionUtils.isEmpty(criteria.getIds())) {

        }
        sql.append("SELECT * FROM service_definition sd WHERE");
        if (name != null) {
            sql.append("AND e.name ILIKE :name ");
            params.put("name", "%" + name + "%");
        }
        if (salary != null) {
            sql.append("AND e.salary > :salary ");
            params.put("salary", salary);
        }
        sql.append("ORDER BY e.salary DESC");

        Query query = entityManager.createNativeQuery(sql.toString(), Employee.class);
        for (Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        return query.getResultList();
    }*/

}
