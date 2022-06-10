package com.zalmanhack.tireshop.controllers;

import com.zalmanhack.tireshop.domains.Timetable;
import com.zalmanhack.tireshop.dtos.BookingDto;
import com.zalmanhack.tireshop.repos.BookingRepo;
import com.zalmanhack.tireshop.services.BookingService;
import com.zalmanhack.tireshop.services.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/booking")
public class BookingController {

    @Value("${entity.datetime.stamp}")
    private String stamp;

    private final BookingService bookingService;
    private final TimetableService timetableService;

    @Autowired
    public BookingController(BookingService bookingService, TimetableService timetableService) {
        this.bookingService = bookingService;
        this.timetableService = timetableService;
    }

    @GetMapping(value = "/get-template")
    public ResponseEntity<Object> getTemplate() {
        return ResponseEntity.ok(new BookingDto());
    }

    //TODO доделать view
    //@JsonView(value = {BookingView.Public.class})
    @PutMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookingRepo add(@Valid @RequestBody BookingDto bookingDto) {
//        bookingService.create(bookingDto);
        return null;
    }

    //TODO добавить документацию
    @GetMapping("/timetable")
    public List<String> getTimetable(@RequestParam(name = "range") short range,
                                         @RequestParam(name = "duration") short duration,
                                         @RequestParam(name = "composite", defaultValue = "false") boolean composite) {
        LocalDate dateNow = LocalDate.now();
        List<Timetable> timetableChanges = timetableService.findTimetableChanges(dateNow, dateNow.plusDays(range));
        System.out.println(timetableChanges);
        return bookingService.findAvailableDateTimeForRangeDays(timetableChanges, range, composite)
                .stream()
                .map(adt -> adt.format(DateTimeFormatter.ofPattern(stamp)))
                .collect(Collectors.toList());
    }
}
