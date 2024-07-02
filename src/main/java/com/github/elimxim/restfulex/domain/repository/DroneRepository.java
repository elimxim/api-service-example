package com.github.elimxim.restfulex.domain.repository;

import com.github.elimxim.restfulex.domain.model.Drone;
import com.github.elimxim.restfulex.domain.model.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    @Query("SELECT d from Drone d where d.state = :state")
    List<Drone> findByState(@Param("state") DroneState state);

    @Query("SELECT count(d) = 0 from Drone d where d.serialNumber = :serialNumber")
    Boolean checkSerialNumber(@Param("serialNumber") String serialNumber);

}
