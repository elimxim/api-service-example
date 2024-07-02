package org.github.elimxim.web;

import org.github.elimxim.service.DroneDispatcher;
import com.github.elimxim.api.DroneDispatcherApi;
import com.github.elimxim.api.model.Drone;
import com.github.elimxim.api.model.DroneFilter;
import com.github.elimxim.api.model.Medication;
import org.github.elimxim.service.exception.HttpResponseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DroneDispatcherApiController implements DroneDispatcherApi {
    private final DroneDispatcher droneDispatcher;

    @Override
    public ResponseEntity<Integer> getDroneBatteryLevel(Long droneId) {
        return ResponseEntity.ok(droneDispatcher.getDroneBatteryLevel(droneId));
    }

    @Override
    public ResponseEntity<List<Medication>> getDroneMedications(Long droneId) {
        return ResponseEntity.ok(droneDispatcher.getDroneMedications(droneId));
    }

    @Override
    public ResponseEntity<List<Drone>> getDrones(DroneFilter filter) {
        if (filter == null) {
            filter = DroneFilter.ALL;
        }

        var drones = droneDispatcher.getDrones(filter == DroneFilter.AVAILABLE);
        return ResponseEntity.ok(drones);
    }

    @Override
    public ResponseEntity<Void> loadMedication(Long droneId, Medication medication) {
        droneDispatcher.loadMedication(droneId, medication);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Drone> registerDrone(Drone drone) {
        drone = droneDispatcher.registerDrone(drone);
        return new ResponseEntity<>(drone, HttpStatus.CREATED);
    }

    @ExceptionHandler(HttpResponseException.class)
    public void httpResponseExceptionHandler(HttpResponseException exception, HttpServletRequest request) {
        log.warn("request {} failed, error: \"{}\"", request.getRequestURI(), exception.getMessage());
        throw exception;
    }
}
