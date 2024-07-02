package com.github.elimxim.restfulex.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DroneNotFoundException extends HttpResponseException {
    public DroneNotFoundException(String message) {
        super(message);
    }
}
