package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.BookedOption;
import com.zalmanhack.tireshop.domains.BookedService;
import com.zalmanhack.tireshop.domains.BookedValue;
import com.zalmanhack.tireshop.domains.templates.TemplateValue;
import com.zalmanhack.tireshop.dtos.BookedOptionDto;
import com.zalmanhack.tireshop.repos.BookedValueRepo;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class BookedValueService {
    private final BookedValueRepo bookedValueRepo;
    private final TemplateValueService templateValueService;

    public BookedValueService(BookedValueRepo bookedValueRepo, TemplateValueService templateValueService) {
        this.bookedValueRepo = bookedValueRepo;
        this.templateValueService = templateValueService;
    }

    public BookedValue create(BookedOption bookedOption, long bookedValueId) {
        TemplateValue templateValue = templateValueService.findById(bookedValueId);
        BookedValue bookedValue = new BookedValue(templateValue);
        bookedValue.setBookedOption(bookedOption);
        return bookedValueRepo.save(bookedValue);
    }
}
