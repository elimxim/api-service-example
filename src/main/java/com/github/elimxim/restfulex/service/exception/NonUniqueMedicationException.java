package com.github.elimxim.restfulex.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class NonUniqueMedicationException extends HttpResponseException {
    public NonUniqueMedicationException(String message) {
        super(message);
    }
}

