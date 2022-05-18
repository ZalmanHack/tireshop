package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zalmanhack.tireshop.domains.abstracts.AbstractOption;
import com.zalmanhack.tireshop.domains.enums.OptionViewType;
import com.zalmanhack.tireshop.utils.DurationConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookedOption extends AbstractOption {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booked_service_id")
    private BookedService bookedService;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booked_value_id")
    private BookedValue bookedValue;
}
