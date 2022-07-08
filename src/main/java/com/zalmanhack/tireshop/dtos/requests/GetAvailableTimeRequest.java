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
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GetAvailableTimeRequest extends AbstractBookingRequest {
    @NotNull
    @Min(1)
    @Max(30)
    private Short rangeDays;
}
