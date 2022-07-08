package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.BookedOption;
import com.zalmanhack.tireshop.domains.BookedValue;
import com.zalmanhack.tireshop.domains.templates.TemplateValue;
import com.zalmanhack.tireshop.repos.BookedValueRepo;
import com.zalmanhack.tireshop.utils.TransactionHandler;
import org.springframework.stereotype.Service;

@Service
public class BookedValueService {
    private final TransactionHandler transactionHandler;
    private final BookedValueRepo bookedValueRepo;
    private final TemplateValueService templateValueService;

    public BookedValueService(TransactionHandler transactionHandler, BookedValueRepo bookedValueRepo, TemplateValueService templateValueService) {
        this.transactionHandler = transactionHandler;
        this.bookedValueRepo = bookedValueRepo;
        this.templateValueService = templateValueService;
    }

    public BookedValue create(BookedOption bookedOption, long bookedValueId) {
        TemplateValue templateValue = templateValueService.findById(bookedValueId);
        BookedValue bookedValue = new BookedValue(templateValue);
        bookedValue.setBookedOption(bookedOption);
        return transactionHandler.runInTransaction(() -> bookedValueRepo.save(bookedValue));
    }
}
