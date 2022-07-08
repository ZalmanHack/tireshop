package com.zalmanhack.tireshop.utils.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        if (duration == null) {
            return 0L;
        }
        return duration.toMinutes();
    }

    @Override
    public Duration convertToEntityAttribute(Long aLong) {
        return Duration.of(aLong, ChronoUnit.MINUTES);
    }
}
