package com.github.elimxim.restfulex.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalDroneWeightException extends HttpResponseException {
    public IllegalDroneWeightException(String message) {
        super(message);
    }
}
