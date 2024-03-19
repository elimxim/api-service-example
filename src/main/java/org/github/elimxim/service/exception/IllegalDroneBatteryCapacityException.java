package org.github.elimxim.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalDroneBatteryCapacityException extends HttpResponseException {
    public IllegalDroneBatteryCapacityException(String message) {
        super(message);
    }
}
