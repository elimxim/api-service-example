package com.musalasoft.artemis.elimxim.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Entity
@Validated
@Getter
@Setter
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9_-]+")
    private String name;
    @NotNull
    @Min(1)
    private Integer weight;
    @NotNull
    @Pattern(regexp = "[A-Z0-9_]+")
    private String code;
    byte[] image;
    @NotNull
    @ManyToOne
    private Drone drone;
}
