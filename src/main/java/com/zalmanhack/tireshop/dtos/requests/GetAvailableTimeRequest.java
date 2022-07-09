package com.zalmanhack.tireshop.dtos.requests;

import com.zalmanhack.tireshop.dtos.BookingDto;
import com.zalmanhack.tireshop.utils.validations.ComplianceCompositions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
//@NoArgsConstructor
public class GetAvailableTimeRequest {
    @NotNull
    @Min(1)
    @Max(30)
    private Short rangeDays;

    @NotNull
    @Valid
    @ComplianceCompositions
    private BookingDto booking;
}
