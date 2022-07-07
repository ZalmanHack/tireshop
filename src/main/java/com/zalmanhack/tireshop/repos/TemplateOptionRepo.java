package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.templates.TemplateOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateOptionRepo extends CrudRepository<TemplateOption, Long> {

}
