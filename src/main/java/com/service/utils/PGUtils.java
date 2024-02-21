package com.service.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PGUtils {

  @Autowired
  private ObjectMapper objectMapper;

  public PGobject getPGObject(Object data) throws SQLException {
    PGobject pGobject = new PGobject();
    pGobject.setType("json");
    pGobject.setValue(
        String.valueOf(objectMapper.convertValue(data, JsonNode.class)));
    return pGobject;
  }
}
