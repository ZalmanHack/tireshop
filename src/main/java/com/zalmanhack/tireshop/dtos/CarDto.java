package com.zalmanhack.tireshop.dtos;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CarDto {

    @Min(0)
    private long id;

    @NotNull
    @Min(0)
    private long userId;

    @NotNull
    @Min(0)
    private long  colorId;

    @NotNull
    @Min(0)
    private double tireDiameter;

    @NotBlank
    @Size(min = 1, max = 64)
    private String model;

    @NotBlank
    @Size(min = 1, max = 16)
    private String stateNumber;
}
