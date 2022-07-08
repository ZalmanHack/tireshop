package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.BookedOption;
import com.zalmanhack.tireshop.domains.BookedService;
import com.zalmanhack.tireshop.domains.Booking;
import com.zalmanhack.tireshop.domains.templates.TemplateService;
import com.zalmanhack.tireshop.dtos.BookedOptionDto;
import com.zalmanhack.tireshop.dtos.BookedServiceDto;
import com.zalmanhack.tireshop.repos.BookedServiceRepo;
import com.zalmanhack.tireshop.utils.TransactionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class BookedServiceService {

    private final TransactionHandler transactionHandler;
    private final BookedServiceRepo bookedServiceRepo;
    private final BookedOptionService bookedOptionService;
    private final TemplateServiceService templateServiceService;

    public BookedServiceService(BookedServiceRepo bookedServiceRepo, BookedOptionService bookedOptionService, TemplateServiceService templateServiceService, TransactionHandler transactionHandler) {
        this.bookedServiceRepo = bookedServiceRepo;
        this.bookedOptionService = bookedOptionService;
        this.templateServiceService = templateServiceService;
        this.transactionHandler = transactionHandler;
    }

    public BookedService create(Booking booking, LocalDateTime startWork, BookedServiceDto bookedServiceDto) {
        TemplateService templateService = templateServiceService.findById(bookedServiceDto.getId());
        BookedService bookedService = new BookedService(templateService);
        bookedService.setDateOfStartWork(startWork);
        bookedService.setBooking(booking);
        BookedService bookedServiceDb = transactionHandler.runInTransaction(() -> bookedServiceRepo.save(bookedService));

        Duration duration = Duration.ZERO;
        long price = 0;

        for (BookedOptionDto bookedOptionDto : bookedServiceDto.getBookedOptions()) {
            BookedOption bookedOption = bookedOptionService.create(bookedServiceDb, bookedOptionDto);
            duration = duration.plus(bookedOption.getBookedValue().getDuration());
            price += bookedOption.getBookedValue().getPrice();
        }

        bookedServiceDb.setDateOfEndWork(startWork.plus(duration));
        bookedServiceDb.setDuration(duration);
        bookedServiceDb.setPrice(price);

        return transactionHandler.runInTransaction(() -> bookedServiceRepo.save(bookedService));
    }
}
