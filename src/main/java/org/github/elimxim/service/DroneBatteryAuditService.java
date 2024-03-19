package org.github.elimxim.service;

import org.github.elimxim.domain.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DroneBatteryAuditService extends QuartzJobBean {
    private final DroneRepository droneRepository;

    @Override
    @Transactional(readOnly = true)
    protected void executeInternal(JobExecutionContext context) {
        var drones = droneRepository.findAll();
        drones.forEach(drone -> {
            log.info("drone {} - state: {}, battery level: {}",
                    drone.getSerialNumber(),
                    drone.getState(),
                    drone.getBatteryLevel()
            );
        });
    }
}
