CREATE TABLE IF NOT EXISTS service
(
    id                  VARCHAR(128)
        CONSTRAINT uk_service
        UNIQUE,
    tenantId            VARCHAR(128),
    serviceDefinitionId VARCHAR(128),
    referenceId         VARCHAR(128),
    createdBy           VARCHAR(128),
    lastModifiedBy      VARCHAR(128),
    createdTime         BIGINT,
    lastModifiedTime    BIGINT,
    additionalDetails   JSONB,
    accountId           VARCHAR(128),
    clientId            VARCHAR(128),
    CONSTRAINT fk_service_service_definition_id FOREIGN KEY (serviceDefinitionId)
        REFERENCES service_definition (id)
);

CREATE TABLE attribute_value
(
    id                VARCHAR(128)
        CONSTRAINT uk_attribute_value
            UNIQUE,
    serviceId         VARCHAR(128),
    attributeCode     VARCHAR(128),
    value             JSONB,
    createdBy         VARCHAR(128),
    lastModifiedBy    VARCHAR(128),
    createdTime       BIGINT,
    lastModifiedTime  BIGINT,
    additionalDetails JSONB,
    CONSTRAINT fk_attribute_definition_service_id FOREIGN KEY (serviceId)
        REFERENCES service (id)
);

