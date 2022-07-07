package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zalmanhack.tireshop.domains.abstracts.AbstractOption;
import com.zalmanhack.tireshop.domains.templates.TemplateOption;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookedOption extends AbstractOption {
    public BookedOption() { }

    public BookedOption(TemplateOption templateOption) {
        super(templateOption.getName());
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booked_service_id")
    private BookedService bookedService;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booked_value_id")
    private BookedValue bookedValue;

}
