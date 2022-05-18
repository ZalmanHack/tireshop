package com.zalmanhack.tireshop.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BookedOptionDto {

    private long id;

    @NotNull
    private long bookedValueId;
}
