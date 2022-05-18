package com.zalmanhack.tireshop.domains.templates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zalmanhack.tireshop.domains.abstracts.AbstractOption;
import com.zalmanhack.tireshop.domains.enums.OptionViewType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TemplateOption extends AbstractOption {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_service_id")
    private TemplateService templateService;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "templateOption")
    private List<TemplateValue> templateValues;

    private OptionViewType viewType;

    @NotNull
    private Boolean required;
}
