package com.github.elimxim.restfulex.service;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroneBatteryAuditConfig {

    @Configuration
    @ConditionalOnProperty(value = "drone.battery.audit.enabled", havingValue = "false")
    @EnableAutoConfiguration(exclude = QuartzAutoConfiguration.class)
    static class DisableScheduler {

    }

    @Configuration
    @RequiredArgsConstructor
    @EnableConfigurationProperties(DroneBatteryAuditProperties.class)
    @ConditionalOnProperty(value = "drone.battery.audit.enabled", havingValue = "true", matchIfMissing = true)
    static class ConfigureScheduler {
        private final DroneBatteryAuditProperties properties;

        @Bean
        public JobDetail droneBatteryCheckerJob() {
            return JobBuilder.newJob()
                    .ofType(DroneBatteryAuditService.class)
                    .storeDurably()
                    .build();
        }

        @Bean
        public Trigger droneBatteryCheckerTrigger() {
            return TriggerBuilder.newTrigger()
                    .forJob(droneBatteryCheckerJob())
                    .withSchedule(CronScheduleBuilder.cronSchedule(properties.getCron()))
                    .build();
        }
    }
}
