package com.zalmanhack.tireshop.dtos;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
public class BookingDto {

    @NotNull
    private Long carId;

    @NotNull
    private Long userId;

//    @NotBlank
//    @Pattern(regexp = "(\\d{2}).(\\d{2}).(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})")
//    private String appointmentDate;

    @Valid
    @NotEmpty
    private List<BookedServiceDto> bookedServices;
}
