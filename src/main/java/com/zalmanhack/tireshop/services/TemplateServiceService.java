package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.templates.TemplateService;
import com.zalmanhack.tireshop.exceptions.RecordNotFoundException;
import com.zalmanhack.tireshop.repos.TemplateServiceRepo;
import com.zalmanhack.tireshop.utils.TransactionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateServiceService {

    private final TransactionHandler transactionHandler;
    private final TemplateServiceRepo templateServiceRepo;

    @Autowired
    public TemplateServiceService(TransactionHandler transactionHandler, TemplateServiceRepo templateServiceRepo) {
        this.transactionHandler = transactionHandler;
        this.templateServiceRepo = templateServiceRepo;
    }


    public TemplateService findById(long id) {
        Optional<TemplateService> optionalTemplateService = transactionHandler.runInTransaction(() -> templateServiceRepo.findById(id));
        if(!optionalTemplateService.isPresent()) {
            throw new RecordNotFoundException(TemplateService.class, id);
        }
        return optionalTemplateService.get();
    }
}
