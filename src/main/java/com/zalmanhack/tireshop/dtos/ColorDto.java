package com.zalmanhack.tireshop.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ColorDto {

    private long id;

    @NotBlank
    @Size(min = 6, max = 6)
    private String hexColor = "FFFFFF";
}
