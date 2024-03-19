package org.github.elimxim.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class NonUniqueDroneException extends HttpResponseException {
    public NonUniqueDroneException(String message) {
        super(message);
    }
}
