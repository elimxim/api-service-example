package com.musalasoft.artemis.elimxim.service;

import com.musalasoft.artemis.elimxim.api.model.Drone;
import com.musalasoft.artemis.elimxim.api.model.DroneModel;
import com.musalasoft.artemis.elimxim.api.model.DroneState;
import com.musalasoft.artemis.elimxim.api.model.Medication;
import com.musalasoft.artemis.elimxim.domain.mapping.DroneDtoMapper;
import com.musalasoft.artemis.elimxim.domain.mapping.MedicationDtoMapper;
import com.musalasoft.artemis.elimxim.domain.repository.DroneRepository;
import com.musalasoft.artemis.elimxim.service.exception.DroneNotFoundException;
import com.musalasoft.artemis.elimxim.service.exception.NonUniqueDroneException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.musalasoft.artemis.elimxim.domain.model.DroneState.IDLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DroneDispatcherTest {
    @Autowired
    private DroneDispatcher droneDispatcher;
    @Autowired
    private DroneRepository droneRepository;
    @Autowired
    private DroneDtoMapper droneDtoMapper;
    @Autowired
    private MedicationDtoMapper medicationDtoMapper;

    @BeforeEach
    void beforeEach() {
        droneRepository.deleteAll();
    }

    @Test
    void testRegisterDroneNonUniqueSerialNumber() {
        var droneDto = new Drone()
                .serialNumber("#SERIAL_000001")
                .model(DroneModel.LIGHTWEIGHT)
                .weightLimit(100)
                .batteryCapacity(40)
                .batteryLevel(100)
                .state(DroneState.IDLE);

        saveDrone(droneDto);

        droneDto.setState(null);

        assertThatThrownBy(() -> droneDispatcher.registerDrone(droneDto))
                .isInstanceOf(NonUniqueDroneException.class)
                .hasMessage("serial number isn't unique");
    }

    @Test
    void testRegisterDrone() {
        assertThat(droneRepository.findAll()).isEmpty();

        var droneDto = new Drone()
                .serialNumber("#SERIAL_000001")
                .model(DroneModel.LIGHTWEIGHT)
                .weightLimit(100)
                .batteryCapacity(40)
                .batteryLevel(100);

        droneDispatcher.registerDrone(droneDto);

        var drones = droneRepository.findAll();
        assertThat(drones).hasSize(1);

        var drone = drones.get(0);

        assertThat(drone.getSerialNumber()).isEqualTo(droneDto.getSerialNumber());
        assertThat(drone.getModel()).isEqualTo(droneDtoMapper.fromDroneModel(droneDto.getModel()));
        assertThat(drone.getWeightLimit()).isEqualTo(droneDto.getWeightLimit());
        assertThat(drone.getBatteryCapacity()).isEqualTo(droneDto.getBatteryCapacity());
        assertThat(drone.getBatteryLevel()).isEqualTo(droneDto.getBatteryLevel());
        assertThat(drone.getState()).isEqualTo(IDLE);
    }

    @Test
    void testGetDrones() {
        assertThat(droneRepository.findAll()).isEmpty();

        var droneDto = new Drone()
                .serialNumber("#SERIAL_000001")
                .model(DroneModel.LIGHTWEIGHT)
                .weightLimit(100)
                .batteryCapacity(40)
                .batteryLevel(100)
                .state(DroneState.IDLE);

        saveDrone(droneDto);

        assertThat(droneRepository.findAll()).hasSize(1);
    }

    @Test
    void testGetDroneMedicationsNotFound() {
        assertThatThrownBy(() -> droneDispatcher.getDroneMedications(1L))
                .isInstanceOf(DroneNotFoundException.class)
                .hasMessage("drone with the specified ID wasn't found");
    }

    @Test
    void testGetDroneMedications() {
        var droneDto = new Drone()
                .serialNumber("#SERIAL_000001")
                .model(DroneModel.LIGHTWEIGHT)
                .weightLimit(100)
                .batteryCapacity(40)
                .batteryLevel(100)
                .state(DroneState.IDLE);

        var drone = saveDrone(droneDto);

        assertThat(droneDispatcher.getDroneMedications(drone.getId())).isEmpty();

        var medicationDto = new Medication()
                .name("TEST_MED_1")
                .weight(100)
                .code("MED_001");

        saveMedication(medicationDto, drone);

        var medications = droneDispatcher.getDroneMedications(drone.getId());
        assertThat(medications).hasSize(1);

        var medication = medications.get(0);

        assertThat(medication.getName()).isEqualTo(medicationDto.getName());
        assertThat(medication.getWeight()).isEqualTo(medicationDto.getWeight());
        assertThat(medication.getCode()).isEqualTo(medicationDto.getCode());
    }

    @Test
    void testGetDroneBatteryLevelNotFound() {
        assertThatThrownBy(() -> droneDispatcher.getDroneBatteryLevel(1L))
                .isInstanceOf(DroneNotFoundException.class)
                .hasMessage("drone with the specified ID wasn't found");
    }

    @Test
    void testGetDroneBatteryLevel() {
        var droneDto = new Drone()
                .serialNumber("#SERIAL_000001")
                .model(DroneModel.LIGHTWEIGHT)
                .weightLimit(100)
                .batteryCapacity(40)
                .batteryLevel(100)
                .state(DroneState.IDLE);

        long droneId = saveDrone(droneDto).getId();

        int batteryLevel = droneDispatcher.getDroneBatteryLevel(droneId);
        assertThat(batteryLevel).isEqualTo(droneDto.getBatteryLevel());
    }

    private com.musalasoft.artemis.elimxim.domain.model.Drone saveDrone(Drone dto) {
        var drone = droneDtoMapper.fromDrone(dto);
        return droneRepository.save(drone);
    }

    private void saveMedication(Medication dto, com.musalasoft.artemis.elimxim.domain.model.Drone drone) {
        var medication = medicationDtoMapper.fromMedication(dto);

        medication.setDrone(drone);
        drone.getMedications().add(medication);

        droneRepository.save(drone);
    }
}
