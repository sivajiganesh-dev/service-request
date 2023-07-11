CREATE TABLE IF NOT EXISTS service_definition
(
    id                VARCHAR(128)
        CONSTRAINT uk_service_definition
            UNIQUE,
    tenantId          VARCHAR(128) NOT NULL,
    code              VARCHAR(128) NOT NULL,
    isActive          boolean,
    createdBy         VARCHAR(128),
    lastModifiedBy    VARCHAR(128),
    createdTime       BIGINT,
    lastModifiedTime  BIGINT,
    additionalDetails JSONB,
    clientId          VARCHAR(128),
    CONSTRAINT pk_service_definition
        PRIMARY KEY (code, tenantId)
);

CREATE TABLE IF NOT EXISTS attribute_definition
(
    id                    VARCHAR(128)
        CONSTRAINT uk_attribute_definition
            UNIQUE,
    serviceDefinitionId VARCHAR(128) NOT NULL,
    tenantId              VARCHAR(128),
    code                  VARCHAR(128) not null,
    dataType              VARCHAR(128),
    values                VARCHAR(1000),
    isActive              BOOLEAN,
    required              BOOLEAN,
    regex                 VARCHAR(128),
    "order"               VARCHAR(128),
    createdBy             VARCHAR(128),
    lastModifiedBy        VARCHAR(128),
    createdTime           BIGINT,
    lastModifiedTime      BIGINT,
    additionalDetails     JSONB,
    CONSTRAINT pk_attribute_definition
        PRIMARY KEY (code, tenantId),
    CONSTRAINT fk_attribute_definition_service_definition_id FOREIGN KEY (serviceDefinitionId)
        REFERENCES service_definition (id)
);
