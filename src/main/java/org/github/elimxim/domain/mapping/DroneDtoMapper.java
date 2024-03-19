package org.github.elimxim.domain.mapping;

import org.github.elimxim.domain.model.Drone;
import org.github.elimxim.api.model.DroneModel;
import org.github.elimxim.api.model.DroneState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DroneDtoMapper {

    List<org.github.elimxim.api.model.Drone> toDrones(List<Drone> source);

    org.github.elimxim.api.model.Drone toDrone(Drone source);

    List<Drone> fromDrones(List<org.github.elimxim.api.model.Drone> source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medications", ignore = true)
    Drone fromDrone(org.github.elimxim.api.model.Drone source);

    default DroneModel toDroneModel(org.github.elimxim.domain.model.DroneModel source) {
        if (source == null) {
            return null;
        }

        return DroneModel.valueOf(source.name());
    }

    default org.github.elimxim.domain.model.DroneModel fromDroneModel(DroneModel source) {
        if (source == null) {
            return null;
        }

        return org.github.elimxim.domain.model.DroneModel.valueOf(source.name());
    }

    default DroneState toDroneState(org.github.elimxim.domain.model.DroneState source) {
        if (source == null) {
            return null;
        }

        return DroneState.fromValue(source.name());
    }

    default org.github.elimxim.domain.model.DroneState fromDroneState(DroneState source) {
        if (source == null) {
            return null;
        }

        return org.github.elimxim.domain.model.DroneState.valueOf(source.name());
    }
}
