package com.zalmanhack.tireshop.dtos.requests;

import com.zalmanhack.tireshop.dtos.BookingDto;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public abstract class AbstractBookingRequest {
    @NotNull
    private Boolean composite;
    @NotNull
    @Valid
    private BookingDto booking;
}
