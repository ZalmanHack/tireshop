package com.zalmanhack.tireshop.dtos.responses;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class AvailableTimeResponse {
    public AvailableTimeResponse() { }

    public AvailableTimeResponse(long durationBooking, List<String> availableTime) {
        this.durationBooking = durationBooking;
        this.availableTime = availableTime;
    }

    @Min(0)
    private long durationBooking;
    @NotBlank
    @Pattern(regexp = "(\\d{2}).(\\d{2}).(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})")
    private List<String> availableTime;


}
