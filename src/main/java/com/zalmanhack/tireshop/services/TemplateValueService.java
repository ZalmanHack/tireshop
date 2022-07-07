package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.Car;
import com.zalmanhack.tireshop.domains.templates.TemplateOption;
import com.zalmanhack.tireshop.domains.templates.TemplateValue;
import com.zalmanhack.tireshop.exceptions.RecordNotFoundException;
import com.zalmanhack.tireshop.repos.TemplateValueRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateValueService {

    private final TemplateValueRepo templateValueRepo;

    public TemplateValueService(TemplateValueRepo templateValueRepo) {
        this.templateValueRepo = templateValueRepo;
    }

    public TemplateValue findById(long id) {
        Optional<TemplateValue> optionalTemplateValue = templateValueRepo.findById(id);
        if(!optionalTemplateValue.isPresent()) {
            throw new RecordNotFoundException(Car.class, id);
        }
        return optionalTemplateValue.get();
    }
}
