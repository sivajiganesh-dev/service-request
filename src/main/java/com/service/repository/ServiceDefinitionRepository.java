package com.service.repository;

import com.service.enums.DataType;
import com.service.model.AttributeDefinition;
import com.service.model.AuditDetails;
import com.service.model.Pagination;
import com.service.model.ServiceDefinition;
import com.service.model.ServiceDefinitionCriteria;
import com.service.model.ServiceDefinitionSearchRequest;
import com.service.utils.PGUtils;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

            String insertQuery = "INSERT INTO service_definition(id,"
                + " tenantId,"
                + " code,"
                + " isActive,"
                + " createdBy,"
                + " lastModifiedBy,"
                + " createdTime,"
                + " lastModifiedTime,"
                + " additionalDetails,"
                + " clientId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertQuery,
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
        jdbcTemplate.batchUpdate(
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
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            attributeDefinitions,
            50,
            (PreparedStatement ps, AttributeDefinition attribute) -> {
                ps.setString(1, attribute.getId());
                ps.setString(2, attribute.getServiceDefinitionId());
                ps.setString(3, attribute.getTenantId());
                ps.setString(4, attribute.getCode());
                ps.setString(5, attribute.getDataType().getValue());
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

        StringBuilder queryBuilder = new StringBuilder("SELECT sd.id service_definition_id, " +
            " sd.tenantId, " +
            " sd.code, " +
            " sd.isActive service_definition_is_active, " +
            " sd.additionalDetails service_definition_additional_details, " +
            " sd.clientId service_definition_client_id, " +
            " sd.createdBy service_created_by, " +
            " sd.lastModifiedBy service_last_modified_by, " +
            " sd.createdTime service_created_time, " +
            " sd.lastModifiedTime service_last_modified_time, " +
            " ad.id attribute_definition_id, " +
            " ad.dataType attribute_definition_data_type, " +
            " ad.\"values\" attribute_definition_values, " +
            " ad.isActive attribute_definition_is_active, " +
            " ad.required attribute_definition_required, " +
            " ad.regex attribute_definition_regex, " +
            " ad.\"order\" attribute_definition_order, " +
            " ad.additionalDetails attribute_definition_additional_details " +
            "FROM service_definition sd " +
            "LEFT JOIN attribute_definition ad ON sd.id = ad.serviceDefinitionId AND sd.tenantId = ad.tenantId WHERE ");
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

        log.info("QUERY ::: {}", query);
        Map<String, ServiceDefinition> serviceDefinitionMap = new HashMap<>();
        jdbcTemplate.query(query, rs -> {
            String serviceDefinitionId = rs.getString("service_definition_id");
            ServiceDefinition sd = serviceDefinitionMap.get(serviceDefinitionId);
            if (sd == null) {
                sd = new ServiceDefinition();
                sd.setId(serviceDefinitionId);
                sd.setTenantId(rs.getString("tenantId"));
                sd.setCode(rs.getString("code"));
                sd.setClientId(rs.getString("service_definition_client_id"));
                sd.setIsActive(rs.getBoolean("service_definition_is_active"));
                AuditDetails ad = new AuditDetails();
                ad.setCreatedBy(rs.getString("service_created_by"));
                ad.setLastModifiedBy(rs.getString("service_last_modified_by"));
                ad.setCreatedTime(rs.getLong("service_created_time"));
                ad.setLastModifiedTime(
                    rs.getLong("service_last_modified_time"));
                sd.setAuditDetails(ad);
                try {
                    sd.setAdditionalDetails((new JSONParser().parse(
                        rs.getString("service_definition_additional_details"))));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                serviceDefinitionMap.put(serviceDefinitionId, sd);
            }

            AttributeDefinition attributeDefinition = new AttributeDefinition();
            attributeDefinition.setId(rs.getString("attribute_definition_id"));
            attributeDefinition.setTenantId(sd.getTenantId());
            attributeDefinition.setCode(sd.getCode());
            String dataType = rs.getString("attribute_definition_data_type");
            if (dataType != null) {
                attributeDefinition.setDataType(
                    Arrays.stream(DataType.values())
                        .filter(dt -> dt.getValue().equals(dataType))
                        .findFirst().get());
            }
            attributeDefinition.setIsActive(rs.getBoolean("attribute_definition_is_active"));
            attributeDefinition.setRequired(rs.getBoolean("attribute_definition_required"));
            attributeDefinition.setAuditDetails(sd.getAuditDetails());
            attributeDefinition.setOrder(rs.getString("attribute_definition_order"));
            attributeDefinition.setRegEx(rs.getString("attribute_definition_regex"));

            sd.getAttributes().add(attributeDefinition);

        });

        List<ServiceDefinition> result = new ArrayList<>(serviceDefinitionMap.values());
        return !result.isEmpty() ? result : Collections.emptyList();
    }

}
