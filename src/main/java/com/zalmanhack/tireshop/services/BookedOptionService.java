package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.BookedOption;
import com.zalmanhack.tireshop.domains.BookedService;
import com.zalmanhack.tireshop.domains.templates.TemplateOption;
import com.zalmanhack.tireshop.dtos.BookedOptionDto;
import com.zalmanhack.tireshop.repos.BookedOptionRepo;
import com.zalmanhack.tireshop.utils.TransactionHandler;
import org.springframework.stereotype.Service;

@Service
public class BookedOptionService {

    private final TransactionHandler transactionHandler;
    private final BookedOptionRepo bookedOptionRepo;
    private final TemplateOptionService templateOptionService;
    private final BookedValueService bookedValueService;

    public BookedOptionService(TransactionHandler transactionHandler, BookedOptionRepo bookedOptionRepo, TemplateOptionService templateOptionService, BookedValueService bookedValueService) {
        this.transactionHandler = transactionHandler;
        this.bookedOptionRepo = bookedOptionRepo;
        this.templateOptionService = templateOptionService;
        this.bookedValueService = bookedValueService;
    }

    public BookedOption create(BookedService bookedService, BookedOptionDto bookedOptionDto) {
        TemplateOption templateOption = templateOptionService.findById(bookedOptionDto.getId());
        BookedOption bookedOption = new BookedOption(templateOption);
        bookedOption.setBookedService(bookedService);
        BookedOption bookedOptionDb = transactionHandler.runInTransaction(() -> bookedOptionRepo.save(bookedOption));
        bookedOption.setBookedValue(bookedValueService.create(bookedOptionDb, bookedOptionDto.getBookedValueId()));
        return transactionHandler.runInTransaction(() -> bookedOptionRepo.save(bookedOptionDb));
    }
}
