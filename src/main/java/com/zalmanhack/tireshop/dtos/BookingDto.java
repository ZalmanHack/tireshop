package com.zalmanhack.tireshop.dtos;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class BookingDto {

    private long id;

    private long carId;

    private long userId;

    @Pattern(regexp = "(\\d{2}).(\\d{2}).(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})")
    private String appointmentDate;

    @Valid
    private List<BookedServiceDto> bookedServices;
}
