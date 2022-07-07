package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zalmanhack.tireshop.domains.abstracts.AbstractValue;
import com.zalmanhack.tireshop.domains.templates.TemplateOption;
import com.zalmanhack.tireshop.domains.templates.TemplateValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.Duration;

@Entity
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
public class BookedValue extends AbstractValue {

    public BookedValue() { }

    public BookedValue(AbstractValue abstractValue) {
        super(abstractValue.getValue(), abstractValue.getDuration(), abstractValue.getPrice());
    }

    @OneToOne(fetch = FetchType.EAGER,
            mappedBy = "bookedValue")
    @JoinColumn(name = "booked_option_id")
    private BookedOption bookedOption;


}
