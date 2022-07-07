package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.templates.TemplateValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateValueRepo extends CrudRepository<TemplateValue, Long> {

}
