package com.zalmanhack.tireshop.domains.templates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zalmanhack.tireshop.domains.abstracts.AbstractService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TemplateService extends AbstractService {

    @OneToMany(mappedBy = "templateService",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<TemplateOption> templateOptions;

}
