package com.github.elimxim.restfulex.domain.mapping;

import com.github.elimxim.restfulex.domain.model.Drone;
import com.github.elimxim.api.model.DroneModel;
import com.github.elimxim.api.model.DroneState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DroneDtoMapper {

    List<com.github.elimxim.api.model.Drone> toDrones(List<Drone> source);

    com.github.elimxim.api.model.Drone toDrone(Drone source);

    List<Drone> fromDrones(List<com.github.elimxim.api.model.Drone> source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medications", ignore = true)
    Drone fromDrone(com.github.elimxim.api.model.Drone source);

    default DroneModel toDroneModel(com.github.elimxim.restfulex.domain.model.DroneModel source) {
        if (source == null) {
            return null;
        }

        return DroneModel.valueOf(source.name());
    }

    default com.github.elimxim.restfulex.domain.model.DroneModel fromDroneModel(DroneModel source) {
        if (source == null) {
            return null;
        }

        return com.github.elimxim.restfulex.domain.model.DroneModel.valueOf(source.name());
    }

    default DroneState toDroneState(com.github.elimxim.restfulex.domain.model.DroneState source) {
        if (source == null) {
            return null;
        }

        return DroneState.fromValue(source.name());
    }

    default com.github.elimxim.restfulex.domain.model.DroneState fromDroneState(DroneState source) {
        if (source == null) {
            return null;
        }

        return com.github.elimxim.restfulex.domain.model.DroneState.valueOf(source.name());
    }
}
