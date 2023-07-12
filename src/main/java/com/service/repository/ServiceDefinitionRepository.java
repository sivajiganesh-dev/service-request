package com.service.repository;

import static com.service.repository.builder.ServiceDefinitionQueryBuilder.BASE_SEARCH_QUERY_SERVICE_DEFINITION;
import static com.service.repository.builder.ServiceDefinitionQueryBuilder.INSERT_INTO_ATTRIBUTE_DEFINITION;
import static com.service.repository.builder.ServiceDefinitionQueryBuilder.INSERT_INTO_SERVICE_DEFINITION;

import com.service.model.AttributeDefinition;
import com.service.model.Pagination;
import com.service.model.ServiceDefinition;
import com.service.model.ServiceDefinitionCriteria;
import com.service.model.ServiceDefinitionSearchRequest;
import com.service.repository.handler.ServiceDefinitionRowCallbackHandler;
import com.service.utils.PGUtils;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
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
public class ServiceDefinitionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PGUtils pgUtils;

    @Transactional
    public ServiceDefinition create(ServiceDefinition serviceDefinition) {
        try {
            serviceDefinition.setId(UUID.randomUUID().toString());
            serviceDefinition.getAttributes().parallelStream().forEach(a -> {
                a.setAuditDetails(serviceDefinition.getAuditDetails());
                a.setId(UUID.randomUUID().toString());
                a.setServiceDefinitionId(serviceDefinition.getId());
            });

            jdbcTemplate.update(INSERT_INTO_SERVICE_DEFINITION,
                serviceDefinition.getId(),
                serviceDefinition.getTenantId(),
                serviceDefinition.getCode(),
                serviceDefinition.getIsActive(),
                serviceDefinition.getAuditDetails().getCreatedBy(),
                serviceDefinition.getAuditDetails().getLastModifiedBy(),
                serviceDefinition.getAuditDetails().getCreatedTime(),
                serviceDefinition.getAuditDetails().getLastModifiedTime(),
                pgUtils.getPGObject(serviceDefinition.getAdditionalDetails()),
                serviceDefinition.getClientId());
            insertAttributes(serviceDefinition.getAttributes());

            return serviceDefinition;
        } catch (Exception ex) {
            log.error("Failed to store service definition");
            throw new RuntimeException(ex);
        }
    }

    private void insertAttributes(List<AttributeDefinition> attributeDefinitions) {
        jdbcTemplate.batchUpdate(INSERT_INTO_ATTRIBUTE_DEFINITION,
            attributeDefinitions,
            50,
            (PreparedStatement ps, AttributeDefinition attribute) -> {
                ps.setString(1, attribute.getId());
                ps.setString(2, attribute.getServiceDefinitionId());
                ps.setString(3, attribute.getTenantId());
                ps.setString(4, attribute.getCode());
                ps.setString(5, attribute.getDataType().toString());
                ps.setString(6, attribute.getValues().toString());
                ps.setBoolean(7, attribute.getIsActive());
                ps.setBoolean(8, attribute.getRequired());
                ps.setString(9, attribute.getRegEx());
                ps.setString(10, attribute.getOrder());
                ps.setString(11, attribute.getAuditDetails().getCreatedBy());
                ps.setString(12, attribute.getAuditDetails().getLastModifiedBy());
                ps.setLong(13, attribute.getAuditDetails().getCreatedTime());
                ps.setLong(14, attribute.getAuditDetails().getLastModifiedTime());
            });
    }

    public List<ServiceDefinition> searchByCriteria(
        ServiceDefinitionSearchRequest searchRequest) {
        ServiceDefinitionCriteria serviceDefinitionCriteria = searchRequest.getServiceDefinitionCriteria();
        Pagination pagination = searchRequest.getPagination();

        StringBuilder queryBuilder = new StringBuilder(BASE_SEARCH_QUERY_SERVICE_DEFINITION);
        if (Objects.nonNull(serviceDefinitionCriteria.getClientId())) {
            queryBuilder
                .append("sd.clientId = '")
                .append(serviceDefinitionCriteria.getClientId())
                .append("' AND ");
        }
        if (Objects.nonNull(serviceDefinitionCriteria.getTenantId())) {
            queryBuilder
                .append("sd.tenantId = '")
                .append(serviceDefinitionCriteria.getTenantId())
                .append("' AND ");
        }
        if (!CollectionUtils.isEmpty(serviceDefinitionCriteria.getIds())) {
            queryBuilder
                .append("sd.id in ('")
                .append(String.join("','", serviceDefinitionCriteria.getIds()))
                .append("') AND ");
        }
        if (!CollectionUtils.isEmpty(serviceDefinitionCriteria.getCode())) {
            queryBuilder
                .append(" sd.code in ('")
                .append(String.join("','", serviceDefinitionCriteria.getCode()))
                .append("') AND ");
        }

        String query = queryBuilder.toString();
        query = query.substring(0, query.length() - 4);

        query = query + " ORDER BY sd.createdtime DESC ";
        if (pagination != null && !(BigDecimal.ZERO.equals(pagination.getLimit())
            || BigDecimal.ZERO.equals(pagination.getOffSet()))) {
            query +=
                " LIMIT " + pagination.getLimit().longValue() + " OFFSET " + pagination.getOffSet()
                    .longValue();
        }

        ServiceDefinitionRowCallbackHandler serviceDefinitionRowCallbackHandler = new ServiceDefinitionRowCallbackHandler();
        jdbcTemplate.query(query, serviceDefinitionRowCallbackHandler);
        return new ArrayList<>(serviceDefinitionRowCallbackHandler.getServiceDefinitionSet());
    }

}
