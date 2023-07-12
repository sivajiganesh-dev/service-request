package com.service.error;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
abstract class BaseException extends RuntimeException {

    protected HttpStatus statusCode;

    protected String error;

    protected int errorCode;

    protected String message;

    protected Map<String, Object> information;

    BaseException(String message, Throwable cause){
        super(message, cause);
    }
}