package com.musalasoft.artemis.elimxim.service;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "drone.battery.audit")
public class DroneBatteryAuditProperties {
    private Boolean enabled = true;
    @NotNull
    private String cron;
}
