package com.zalmanhack.tireshop.dtos.requests;

import com.zalmanhack.tireshop.dtos.BookingDto;
import com.zalmanhack.tireshop.utils.validations.ComplianceCompositions;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CreateBookingRequest {
    @NotBlank
    @Pattern(regexp = "(\\d{2}).(\\d{2}).(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})")
    private String appointmentDate;

    @NotNull
    @Valid
    @ComplianceCompositions
    private BookingDto booking;
}
