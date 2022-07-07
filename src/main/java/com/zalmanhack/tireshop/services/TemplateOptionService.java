package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.Car;
import com.zalmanhack.tireshop.domains.templates.TemplateOption;
import com.zalmanhack.tireshop.domains.templates.TemplateService;
import com.zalmanhack.tireshop.exceptions.RecordNotFoundException;
import com.zalmanhack.tireshop.repos.TemplateOptionRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateOptionService {
    private final TemplateOptionRepo templateOptionRepo;

    public TemplateOptionService(TemplateOptionRepo templateOptionRepo) {
        this.templateOptionRepo = templateOptionRepo;
    }

    public TemplateOption findById(long id) {
        Optional<TemplateOption> optionalTemplateOption = templateOptionRepo.findById(id);
        if(!optionalTemplateOption.isPresent()) {
            throw new RecordNotFoundException(Car.class, id);
        }
        return optionalTemplateOption.get();
    }
}
