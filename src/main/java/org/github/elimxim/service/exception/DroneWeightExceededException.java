package org.github.elimxim.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DroneWeightExceededException extends HttpResponseException {
    public DroneWeightExceededException(String message) {
        super(message);
    }
}
