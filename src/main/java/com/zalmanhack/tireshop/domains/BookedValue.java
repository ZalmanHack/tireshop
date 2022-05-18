package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zalmanhack.tireshop.domains.abstracts.AbstractValue;
import com.zalmanhack.tireshop.domains.templates.TemplateOption;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
public class BookedValue extends AbstractValue {

    @OneToOne(fetch = FetchType.EAGER,
            mappedBy = "bookedValue")
    @JoinColumn(name = "booked_option_id")
    private BookedOption bookedOption;
}
