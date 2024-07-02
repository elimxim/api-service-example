package org.github.elimxim.service.validator;

import com.github.elimxim.api.model.Drone;
import com.github.elimxim.api.model.DroneModel;
import org.github.elimxim.service.Messages;
import org.github.elimxim.service.exception.IllegalDroneBatteryCapacityException;
import org.github.elimxim.service.exception.IllegalDroneWeightException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DroneDtoValidator {
    private final Messages messages;

    private final Map<DroneModel, RangeClosed> weightLimitMap = new EnumMap<>(DroneModel.class) {{
        put(DroneModel.LIGHTWEIGHT, new RangeClosed(1, 100));
        put(DroneModel.MIDDLEWEIGHT, new RangeClosed(101, 200));
        put(DroneModel.CRUISERWEIGHT, new RangeClosed(201, 350));
        put(DroneModel.HEAVYWEIGHT, new RangeClosed(351, 500));
    }};

    private final Map<DroneModel, RangeClosed> batteryCapacityMap = new EnumMap<>(DroneModel.class) {{
        put(DroneModel.LIGHTWEIGHT, new RangeClosed(10, 40));
        put(DroneModel.MIDDLEWEIGHT, new RangeClosed(41, 60));
        put(DroneModel.CRUISERWEIGHT, new RangeClosed(61, 80));
        put(DroneModel.HEAVYWEIGHT, new RangeClosed(81, 100));
    }};

    public void validate(Drone drone) {
        var weightLimitRange = weightLimitMap.get(drone.getModel());
        if (!weightLimitRange.between(drone.getWeightLimit())) {
            String error = messages.get(
                    "validation.drone.weightLimit.error",
                    List.of(weightLimitRange.low, weightLimitRange.high)
            );

            throw new IllegalDroneWeightException(error);
        }

        var batteryCapacityRange = batteryCapacityMap.get(drone.getModel());
        if (!batteryCapacityRange.between(drone.getBatteryCapacity())) {
            String error = messages.get(
                    "validation.drone.batteryCapacity.error",
                    List.of(batteryCapacityRange.low, batteryCapacityRange.high)
            );

            throw new IllegalDroneBatteryCapacityException(error);
        }
    }

    private record RangeClosed(int low, int high) {
        public boolean between(int value) {
            return low <= value && value <= high;
        }
    }
}
