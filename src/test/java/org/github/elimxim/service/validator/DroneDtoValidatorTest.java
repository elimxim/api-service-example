package org.github.elimxim.service.validator;

import org.github.elimxim.api.model.Drone;
import org.github.elimxim.api.model.DroneModel;
import org.github.elimxim.service.exception.IllegalDroneBatteryCapacityException;
import org.github.elimxim.service.exception.IllegalDroneWeightException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class DroneDtoValidatorTest {

    @Autowired
    private DroneDtoValidator validator;

    @Test
    void testLightweightDroneWeightLimitIllegal() {
        var drone = new Drone()
                .model(DroneModel.LIGHTWEIGHT)
                .weightLimit(500)
                .batteryCapacity(40);

        assertThatThrownBy(() -> validator.validate(drone))
                .isInstanceOf(IllegalDroneWeightException.class)
                .hasMessage("drone weight must be between 1 and 100 gram");
    }

    @Test
    void testLightweightDroneWeightLimitSuccess() {
        var drone = new Drone()
                .model(DroneModel.LIGHTWEIGHT)
                .weightLimit(100)
                .batteryCapacity(40);

        var e = catchException(() -> validator.validate(drone));
        assertThat(e).isNull();
    }

    @Test
    void testLightweightDroneBatteryCapacityIllegal() {
        var drone = new Drone()
                .model(DroneModel.LIGHTWEIGHT)
                .weightLimit(100)
                .batteryCapacity(100);

        assertThatThrownBy(() -> validator.validate(drone))
                .isInstanceOf(IllegalDroneBatteryCapacityException.class)
                .hasMessage("drone battery capacity must be between 10 and 40 percent");
    }

    @Test
    void testLightweightDroneBatteryCapacitySuccess() {
        var drone = new Drone()
                .model(DroneModel.LIGHTWEIGHT)
                .weightLimit(100)
                .batteryCapacity(40);

        var e = catchException(() -> validator.validate(drone));
        assertThat(e).isNull();
    }
}
