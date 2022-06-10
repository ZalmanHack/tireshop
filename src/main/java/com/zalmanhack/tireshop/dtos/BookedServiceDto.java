package com.zalmanhack.tireshop.dtos;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class BookedServiceDto {

    private long id;

    @Valid
    @NotEmpty
    private List<BookedOptionDto> bookedOptions;

    @NotBlank
    @Pattern(regexp = "(\\d{2}).(\\d{2}).(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})")
    private String dateOfStartWork;
}
