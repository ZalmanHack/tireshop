package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.BookedOption;
import com.zalmanhack.tireshop.domains.BookedService;
import com.zalmanhack.tireshop.domains.templates.TemplateOption;
import com.zalmanhack.tireshop.dtos.BookedOptionDto;
import com.zalmanhack.tireshop.repos.BookedOptionRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookedOptionService {

    private final BookedOptionRepo bookedOptionRepo;
    private final TemplateOptionService templateOptionService;
    private final BookedValueService bookedValueService;

    public BookedOptionService(BookedOptionRepo bookedOptionRepo, TemplateOptionService templateOptionService, BookedValueService bookedValueService) {
        this.bookedOptionRepo = bookedOptionRepo;
        this.templateOptionService = templateOptionService;
        this.bookedValueService = bookedValueService;
    }

    public BookedOption create(BookedService bookedService, BookedOptionDto bookedOptionDto) {
        TemplateOption templateOption = templateOptionService.findById(bookedOptionDto.getId());
        BookedOption bookedOption = new BookedOption(templateOption);
        bookedOption.setBookedService(bookedService);
        BookedOption bookedOptionDb = bookedOptionRepo.save(bookedOption);

        bookedOption.setBookedValue(bookedValueService.create(bookedOptionDb, bookedOptionDto.getBookedValueId()));

        return bookedOptionRepo.save(bookedOptionDb);
    }
}
