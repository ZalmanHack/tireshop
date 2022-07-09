package com.zalmanhack.tireshop.exceptions;

import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.SimpleFormatter;

public class NotAvailableDateTimeException extends RuntimeException {

    public <T> NotAvailableDateTimeException(LocalDateTime appointmentDateTime, String patternDateTime) {
        super(String.format("Unable to make a booking for %s",appointmentDateTime.format(DateTimeFormatter.ofPattern(patternDateTime))));
    }

    public <T> NotAvailableDateTimeException(LocalDate startDate, LocalDate endDate) {
        super(String.format("Unable to find available time between %s and %s", startDate, endDate));
    }
}
