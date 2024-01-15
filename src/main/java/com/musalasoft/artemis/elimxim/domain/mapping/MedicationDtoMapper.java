package com.musalasoft.artemis.elimxim.domain.mapping;

import com.musalasoft.artemis.elimxim.domain.model.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MedicationDtoMapper {

    List<com.musalasoft.artemis.elimxim.api.model.Medication> toMedications(List<Medication> source);

    List<Medication> fromMedications(List<com.musalasoft.artemis.elimxim.api.model.Medication> source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "drone", ignore = true)
    Medication fromMedication(com.musalasoft.artemis.elimxim.api.model.Medication source);


}
