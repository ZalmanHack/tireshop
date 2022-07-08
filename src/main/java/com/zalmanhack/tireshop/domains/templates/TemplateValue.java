package com.zalmanhack.tireshop.domains.templates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zalmanhack.tireshop.domains.abstracts.AbstractValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TemplateValue extends AbstractValue {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_option_id")
    private TemplateOption templateOption;
}
