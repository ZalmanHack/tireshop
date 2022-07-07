package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.templates.TemplateService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateServiceRepo extends CrudRepository<TemplateService, Long> {

}
