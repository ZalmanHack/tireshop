package com.zalmanhack.tireshop.domains.abstracts;

import com.zalmanhack.tireshop.utils.DurationConverter;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@ToString(includeFieldNames = true)
public abstract class AbstractService {

    public AbstractService() {}

    public AbstractService(String name, boolean composite, Duration intervalToOrder) {
        this.name = name;
        this.composite = composite;
        this.intervalToOrder = intervalToOrder;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(length = 32)
    @Size(min = 1, max = 32)
    private String name;

    @NotNull
    private boolean composite = false;

    @Convert(converter = DurationConverter.class)
    private Duration intervalToOrder;


}
