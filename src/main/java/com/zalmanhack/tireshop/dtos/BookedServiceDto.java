package com.zalmanhack.tireshop.dtos;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class BookedServiceDto {

    @Id
    private long id;

    @Valid
    private List<BookedOptionDto> bookedOptions;

    @Pattern(regexp = "(\\d{2}).(\\d{2}).(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})")
    private String dateOfStartWork;

    @Pattern(regexp = "(\\d{2}).(\\d{2}).(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})")
    private String dateOfEndWork;
}
