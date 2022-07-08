package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.templates.TemplateOption;
import com.zalmanhack.tireshop.exceptions.RecordNotFoundException;
import com.zalmanhack.tireshop.repos.TemplateOptionRepo;
import com.zalmanhack.tireshop.utils.TransactionHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateOptionService {
    private final TransactionHandler transactionHandler;
    private final TemplateOptionRepo templateOptionRepo;

    public TemplateOptionService(TransactionHandler transactionHandler, TemplateOptionRepo templateOptionRepo) {
        this.transactionHandler = transactionHandler;
        this.templateOptionRepo = templateOptionRepo;
    }

    public TemplateOption findById(long id) {
        Optional<TemplateOption> optionalTemplateOption = transactionHandler.runInTransaction(() -> templateOptionRepo.findById(id));
        if(!optionalTemplateOption.isPresent()) {
            throw new RecordNotFoundException(TemplateOption.class, id);
        }
        return optionalTemplateOption.get();
    }
}
