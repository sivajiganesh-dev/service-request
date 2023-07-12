package com.service.repository;

import static com.service.repository.builder.ServiceQueryBuilder.INSERT_INTO_ATTRIBUTE_VALUE;
import static com.service.repository.builder.ServiceQueryBuilder.INSERT_INTO_SERVICE;

import com.service.model.AttributeValue;
import com.service.model.Service;
import com.service.model.ServiceSearchRequest;
import com.service.repository.builder.ServiceQueryBuilder;
import com.service.repository.handler.ServiceRowCallbackHandler;
import com.service.utils.PGUtils;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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

            jdbcTemplate.update(INSERT_INTO_SERVICE,
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
        jdbcTemplate.batchUpdate(INSERT_INTO_ATTRIBUTE_VALUE, attributeValues, 50,
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
        ServiceQueryBuilder queryBuilder = new ServiceQueryBuilder(
            searchRequest.getServiceCriteria(), searchRequest.getPagination());

        String searchQuery = queryBuilder.createSearchCriteriaQuery();
        log.info("Search Query ::: {}", searchQuery);

        ServiceRowCallbackHandler serviceRowCallbackHandler = new ServiceRowCallbackHandler();
        jdbcTemplate.query(searchQuery, serviceRowCallbackHandler);
        return serviceRowCallbackHandler.getServiceList().isEmpty()
            ? Collections.emptyList()
            : new ArrayList<>(serviceRowCallbackHandler.getServiceList());
    }
}
