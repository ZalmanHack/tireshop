package com.zalmanhack.tireshop.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CarDto {

    private long id;

    private long userId;

    private long  colorId;

    private double tireDiameter;

    @NotBlank
    @Size(min = 1, max = 64)
    private String model;

    @NotBlank
    @Size(min = 1, max = 16)
    private String stateNumber;
}
