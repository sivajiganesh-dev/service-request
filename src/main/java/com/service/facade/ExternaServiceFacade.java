package com.service.facade;

import com.service.dto.AddressDto;
import com.service.dto.UserDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ExternaServiceFacade {

  @Value("${app.external.service.baseurl}")
  private String baseUrl;

  @Autowired
  private RestTemplate restTemplate;

  public AddressDto getAddressDetails() {
    try {
      ResponseEntity<UserDto> responseEntity = restTemplate.exchange(
          baseUrl + "/v2/users?size=1", HttpMethod.GET, new HttpEntity<>(constructHeaders()),
          new ParameterizedTypeReference<>() {
          }, new Object[0]);
      return responseEntity.getBody().getAddress();
    } catch (Exception ex) {
      log.error("Failed to fetch user details from external service", ex);
      throw new RuntimeException(ex);
    }
  }

  private HttpHeaders constructHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    return headers;
  }

}
