package com.zalmanhack.tireshop.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zalmanhack.tireshop.domains.Booking;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CreateBookingResponse {

    public CreateBookingResponse(Booking booking, String datePattern) {
        this.appointmentDate = booking.getAppointmentDate().format(DateTimeFormatter.ofPattern(datePattern));
        this.orderStatus = booking.getOrderStatus().name();
        this.duration = booking.getDuration().toMinutes();
        this.price = booking.getPrice();
        this.id = booking.getId();

    }

    @NotBlank
    private String appointmentDate;
    @NotBlank
    private String orderStatus;
    @Min(0)
    private long duration;
    @Min(0)
    private long price;
    private long id;
}
