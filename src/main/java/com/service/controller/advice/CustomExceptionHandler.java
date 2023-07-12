package com.service.controller.advice;


import static com.service.error.response.StatusResponse.Type.ERROR;

import com.service.error.response.GenericResponse;
import com.service.error.response.StatusResponse;
import com.service.model.ResponseInfo;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Error encountered :::", ex);
        GenericResponse genericResponse =
            GenericResponse.builder()
                .errors(Collections.singletonList(StatusResponse.builder()
                    .statusCode(400)
                    .statusMessage(ex.getMessage())
                    .statusType(ERROR)
                    .build()))
                .responseInfo(ResponseInfo.builder().build())
                .build();
        return new ResponseEntity<Object>(genericResponse, HttpStatus.BAD_REQUEST);
    }
}
