package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.BookedService;
import com.zalmanhack.tireshop.domains.Booking;
import com.zalmanhack.tireshop.domains.templates.TemplateService;
import com.zalmanhack.tireshop.dtos.BookedOptionDto;
import com.zalmanhack.tireshop.dtos.BookedServiceDto;
import com.zalmanhack.tireshop.repos.BookedServiceRepo;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class BookedServiceService {

    private final BookedServiceRepo bookedServiceRepo;
    private final BookedOptionService bookedOptionService;
    private final TemplateServiceService templateServiceService;

    public BookedServiceService(BookedServiceRepo bookedServiceRepo, BookedOptionService bookedOptionService, TemplateServiceService templateServiceService) {
        this.bookedServiceRepo = bookedServiceRepo;
        this.bookedOptionService = bookedOptionService;
        this.templateServiceService = templateServiceService;
    }

    public BookedService create(Booking booking, LocalDateTime startWork, BookedServiceDto bookedServiceDto) {
        TemplateService templateService = templateServiceService.findById(bookedServiceDto.getId());
        BookedService bookedService = new BookedService(templateService);
        bookedService.setDateOfStartWork(startWork);
        bookedService.setBooking(booking);
        BookedService bookedServiceDb = bookedServiceRepo.save(bookedService);

        Duration duration = Duration.ZERO;
        for (BookedOptionDto bookedOptionDto : bookedServiceDto.getBookedOptions()) {
            duration = duration.plus(bookedOptionService.create(bookedServiceDb, bookedOptionDto).getBookedValue().getDuration());
        }

        bookedServiceDb.setDateOfEndWork(startWork.plus(duration));
        return bookedServiceRepo.save(bookedService);
    }
}
