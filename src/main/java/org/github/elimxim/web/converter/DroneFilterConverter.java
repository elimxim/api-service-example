package org.github.elimxim.web.converter;

import org.github.elimxim.api.model.DroneFilter;
import org.springframework.core.convert.converter.Converter;

public class DroneFilterConverter implements Converter<String, DroneFilter> {

    @Override
    public DroneFilter convert(String value) {
        return DroneFilter.fromValue(value.toLowerCase());
    }
}
