package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.Car;
import com.zalmanhack.tireshop.domains.templates.TemplateService;
import com.zalmanhack.tireshop.exceptions.RecordNotFoundException;
import com.zalmanhack.tireshop.repos.TemplateServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateServiceService {

    private final TemplateServiceRepo templateServiceRepo;

    @Autowired
    public TemplateServiceService(TemplateServiceRepo templateServiceRepo) {
        this.templateServiceRepo = templateServiceRepo;
    }


    public TemplateService findById(long id) {
        Optional<TemplateService> optionalTemplateService = templateServiceRepo.findById(id);
        if(!optionalTemplateService.isPresent()) {
            throw new RecordNotFoundException(Car.class, id);
        }
        return optionalTemplateService.get();
    }
}
