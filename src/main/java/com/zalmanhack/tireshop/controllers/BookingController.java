package com.zalmanhack.tireshop.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.zalmanhack.tireshop.domains.*;
import com.zalmanhack.tireshop.domains.enums.Role;
import com.zalmanhack.tireshop.dtos.BookingDto;
import com.zalmanhack.tireshop.repos.BookingRepo;
import com.zalmanhack.tireshop.services.*;
import com.zalmanhack.tireshop.views.BookingView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/booking")
public class BookingController {

    @Value("${entity.datetime.stamp}")
    private String stamp;


    private final ModelMapper modelMapper;
    private final BookingService bookingService;
    private final TimetableService timetableService;
    private final CarService carService;
    private final UserService userService;
    private final BookedServiceService bookedServiceService;

    @Autowired
    public BookingController(ModelMapper modelMapper, BookingService bookingService, TimetableService timetableService, CarService carService, UserService userService, BookedServiceService bookedServiceService) {
        this.modelMapper = modelMapper;
        this.bookingService = bookingService;
        this.timetableService = timetableService;
        this.carService = carService;
        this.userService = userService;
        this.bookedServiceService = bookedServiceService;
    }

    @RolesAllowed(Role.Names.ADMIN)
    @GetMapping(value = "/get-template")
    public ResponseEntity<Object> getTemplate(@AuthenticationPrincipal UserDetails user) {
        System.out.println(user.getAuthorities());
        return ResponseEntity.ok(new BookingDto());
    }

    //TODO доделать view
    @JsonView(value = {BookingView.Public.class})
    @PutMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Booking add(@Valid @RequestBody BookingDto bookingDto) {
        User user = userService.findById(bookingDto.getUserId());
        Car car = carService.findById(bookingDto.getCarId());
        return bookingService.create(user, car, bookingDto);
    }

    //TODO добавить документацию
    @GetMapping("/timetable")
    public List<String> getTimetable(@RequestParam(name = "range") short range,
                                             @RequestParam(name = "duration") short duration,
                                         @RequestParam(name = "composite", defaultValue = "false") boolean composite) {
        LocalDate currentDate = LocalDate.of(2022,6,10);
        LocalDate endDate = currentDate.plusDays(range - 1);
        List<Timetable> timetableChanges = timetableService.findTimetableChanges(currentDate, endDate);

        List<LocalDateTime> availableDateTime = bookingService.findAvailableDateTimeForRangeDates(timetableChanges, currentDate, endDate, composite);

        return bookingService.findAvailableDateTimeForRangeDates(timetableChanges, currentDate, endDate, composite)
                .stream()
                .map(adt -> adt.format(DateTimeFormatter.ofPattern(stamp)))
                .collect(Collectors.toList());
    }
}
