package com.service.error;

import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class ManagerException extends BaseException {

    @Builder
    ManagerException(String message, HttpStatus statusCode, String error,
                     int errorCode, Throwable cause, Map<String, Object> information) {
        super(message, cause);
        this.statusCode = statusCode;
        this.error = error;
        this.errorCode = errorCode;
        this.message = message;
        this.information = information;
    }
}