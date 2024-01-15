package com.musalasoft.artemis.elimxim.domain.mapping;

import com.musalasoft.artemis.elimxim.domain.model.Drone;
import com.musalasoft.artemis.elimxim.api.model.DroneModel;
import com.musalasoft.artemis.elimxim.api.model.DroneState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DroneDtoMapper {

    List<com.musalasoft.artemis.elimxim.api.model.Drone> toDrones(List<Drone> source);

    com.musalasoft.artemis.elimxim.api.model.Drone toDrone(Drone source);

    List<Drone> fromDrones(List<com.musalasoft.artemis.elimxim.api.model.Drone> source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medications", ignore = true)
    Drone fromDrone(com.musalasoft.artemis.elimxim.api.model.Drone source);

    default DroneModel toDroneModel(com.musalasoft.artemis.elimxim.domain.model.DroneModel source) {
        if (source == null) {
            return null;
        }

        return DroneModel.valueOf(source.name());
    }

    default com.musalasoft.artemis.elimxim.domain.model.DroneModel fromDroneModel(DroneModel source) {
        if (source == null) {
            return null;
        }

        return com.musalasoft.artemis.elimxim.domain.model.DroneModel.valueOf(source.name());
    }

    default DroneState toDroneState(com.musalasoft.artemis.elimxim.domain.model.DroneState source) {
        if (source == null) {
            return null;
        }

        return DroneState.fromValue(source.name());
    }

    default com.musalasoft.artemis.elimxim.domain.model.DroneState fromDroneState(DroneState source) {
        if (source == null) {
            return null;
        }

        return com.musalasoft.artemis.elimxim.domain.model.DroneState.valueOf(source.name());
    }
}
