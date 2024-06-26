package com.github.elimxim.restfulex.service;

import com.github.elimxim.api.model.Drone;
import com.github.elimxim.api.model.Medication;
import com.github.elimxim.restfulex.service.exception.*;
import com.github.elimxim.restfulex.service.validator.DroneDtoValidator;
import com.github.elimxim.restfulex.domain.mapping.DroneDtoMapper;
import com.github.elimxim.restfulex.domain.mapping.MedicationDtoMapper;
import com.github.elimxim.restfulex.domain.model.DroneState;
import com.github.elimxim.restfulex.domain.repository.DroneRepository;
import com.github.elimxim.restfulex.domain.repository.MedicationRepository;
import com.github.elimxim.restfulex.service.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneDispatcher {
    private final DroneRepository droneRepository;
    private final DroneDtoMapper droneDtoMapper;
    private final MedicationRepository medicationRepository;
    private final MedicationDtoMapper medicationDtoMapper;
    private final DroneDtoValidator droneDtoValidator;
    private final Messages messages;

    @Transactional(propagation = Propagation.REQUIRED)
    public Drone registerDrone(Drone droneDto) {
        if (!droneRepository.checkSerialNumber(droneDto.getSerialNumber())) {
            String error = messages.get("validation.drone.serialNumber.nonUnique.error");
            throw new NonUniqueDroneException(error);
        }

        droneDtoValidator.validate(droneDto);

        var drone = droneDtoMapper.fromDrone(droneDto);
        drone.setState(DroneState.IDLE);
        drone = droneRepository.save(drone);

        return droneDtoMapper.toDrone(drone);
    }

    @Transactional(readOnly = true)
    public List<Drone> getDrones(boolean availableOnly) {
        List<com.github.elimxim.restfulex.domain.model.Drone> drones;
        if (availableOnly) {
            drones = droneRepository.findByState(DroneState.IDLE);
        } else {
            drones = droneRepository.findAll();
        }
        return droneDtoMapper.toDrones(drones);
    }

    @Transactional(readOnly = true)
    public List<Medication> getDroneMedications(Long droneId) {
        var droneOptional = droneRepository.findById(droneId);
        if (droneOptional.isEmpty()) {
            String error = messages.get("validation.drone.notFound.error");
            throw new DroneNotFoundException(error);
        }

        var medications = droneOptional.get().getMedications();
        return medicationDtoMapper.toMedications(medications);
    }

    @Transactional(readOnly = true)
    public Integer getDroneBatteryLevel(Long droneId) {
        var droneOptional = droneRepository.findById(droneId);
        if (droneOptional.isEmpty()) {
            String error = messages.get("validation.drone.notFound.error");
            throw new DroneNotFoundException(error);
        }

        return droneOptional.get().getBatteryLevel();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void loadMedication(Long droneId, Medication medicationDto) {
        var droneOptional = droneRepository.findById(droneId);
        if (droneOptional.isEmpty()) {
            String error = messages.get("validation.drone.notFound.error");
            throw new DroneNotFoundException(error);
        }

        if (!medicationRepository.checkCode(medicationDto.getCode())) {
            String error = messages.get("validation.medication.code.nonUnique.error");
            throw new NonUniqueMedicationException(error);
        }

        var drone = droneOptional.get();

        if (drone.getBatteryLevel() < 25) {
            String error = messages.get("validation.drone.batteryLevel.error");
            throw new DroneLowBatteryLevelException(error);
        }

        int weightLimit = drone.getWeightLimit();
        int occupiedWeight = (int) drone.getMedications().stream()
                .map(com.github.elimxim.restfulex.domain.model.Medication::getWeight)
                .count();

        int availableWeight = weightLimit - occupiedWeight;

        if (availableWeight < medicationDto.getWeight()) {
            String error = messages.get("validation.drone.weightExceeded.error");
            throw new DroneWeightExceededException(error);
        }

        var medication = medicationDtoMapper.fromMedication(medicationDto);

        medication.setDrone(drone);
        drone.getMedications().add(medication);

        droneRepository.save(drone);
    }
}
