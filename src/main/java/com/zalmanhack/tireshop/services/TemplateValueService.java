package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.templates.TemplateValue;
import com.zalmanhack.tireshop.exceptions.RecordNotFoundException;
import com.zalmanhack.tireshop.repos.TemplateValueRepo;
import com.zalmanhack.tireshop.utils.TransactionHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateValueService {

    private final TransactionHandler transactionHandler;
    private final TemplateValueRepo templateValueRepo;

    public TemplateValueService(TransactionHandler transactionHandler, TemplateValueRepo templateValueRepo) {
        this.transactionHandler = transactionHandler;
        this.templateValueRepo = templateValueRepo;
    }

    public TemplateValue findById(long id) {
        Optional<TemplateValue> optionalTemplateValue = transactionHandler.runInTransaction(() -> templateValueRepo.findById(id));
        if(!optionalTemplateValue.isPresent()) {
            throw new RecordNotFoundException(TemplateValue.class, id);
        }
        return optionalTemplateValue.get();
    }
}
