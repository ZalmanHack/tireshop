package com.zalmanhack.tireshop.dtos.requests;

import com.zalmanhack.tireshop.dtos.BookingDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateBookingRequest extends AbstractBookingRequest {
    @NotBlank
    @Pattern(regexp = "(\\d{2}).(\\d{2}).(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})")
    private String appointmentDate;
}
