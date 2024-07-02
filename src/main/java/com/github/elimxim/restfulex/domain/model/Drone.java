package com.github.elimxim.restfulex.domain.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Entity
@Validated
@Getter
@Setter
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Length(min = 1, max = 100)
    private String serialNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DroneModel model;
    @NotNull
    @Min(0) @Max(500)
    private Integer weightLimit;
    @NotNull
    @Min(0) @Max(100)
    private Integer batteryCapacity;
    @NotNull
    @Min(0) @Max(100)
    private Integer batteryLevel;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DroneState state;
    @Valid
    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
    private List<Medication> medications = new ArrayList<>();
}
