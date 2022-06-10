package com.zalmanhack.tireshop.domains.abstracts;

import com.zalmanhack.tireshop.utils.DurationConverter;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;

@Data
@MappedSuperclass
@ToString(includeFieldNames = true)
public class AbstractValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(length = 32)
    @Size(min = 1, max = 32)
    private String value;

    @Convert(converter = DurationConverter.class)
    private Duration duration;

    @NotNull
    private long price;
}
