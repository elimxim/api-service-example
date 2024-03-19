package org.github.elimxim.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DroneLowBatteryLevelException extends HttpResponseException {
    public DroneLowBatteryLevelException(String message) {
        super(message);
    }
}
