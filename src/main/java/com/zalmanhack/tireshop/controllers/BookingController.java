package com.zalmanhack.tireshop.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.zalmanhack.tireshop.domains.*;
import com.zalmanhack.tireshop.dtos.BookingDto;
import com.zalmanhack.tireshop.dtos.requests.CreateBookingRequest;
import com.zalmanhack.tireshop.dtos.requests.GetAvailableTimeRequest;
import com.zalmanhack.tireshop.dtos.responses.AvailableTimeResponse;
import com.zalmanhack.tireshop.services.*;
import com.zalmanhack.tireshop.utils.validations.ComplianceCompositions;
import com.zalmanhack.tireshop.views.BookingView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
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
    private final TemplateServiceService templateServiceService;

    @Autowired
    public BookingController(ModelMapper modelMapper, BookingService bookingService, TimetableService timetableService, CarService carService, UserService userService, TemplateServiceService templateServiceService) {
        this.modelMapper = modelMapper;
        this.bookingService = bookingService;
        this.timetableService = timetableService;
        this.carService = carService;
        this.userService = userService;
        this.templateServiceService = templateServiceService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/get-template")
    public ResponseEntity<Object> getTemplate(@AuthenticationPrincipal UserDetailsImpl user) {
        System.out.println(user.getEmail());
        return ResponseEntity.ok(new BookingDto());
    }

    //TODO доделать view
    @JsonView(value = {BookingView.Public.class})
    @PutMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Booking add(@Valid @RequestBody CreateBookingRequest createBookingRequest) {
        BookingDto bookingDto = createBookingRequest.getBooking();
        User user = userService.findById(bookingDto.getUserId());
        Car car = carService.findById(bookingDto.getCarId());
        return bookingService.create(user, car, bookingDto, modelMapper.map(createBookingRequest.getAppointmentDate(), LocalDateTime.class));
    }

    //TODO добавить документацию
    @PostMapping("/available-time")
    public AvailableTimeResponse getTimetable(@Valid @ComplianceCompositions @RequestBody GetAvailableTimeRequest getAvailableTimeRequest) {
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.plusDays(getAvailableTimeRequest.getRangeDays() - 1);
        List<Timetable> timetableChanges = timetableService.findTimetableChanges(currentDate, endDate);
        List<LocalDateTime> allAvailableDateTime = bookingService.findAvailableDateTimeForRangeDates(timetableChanges, currentDate, endDate, getAvailableTimeRequest.getComposite());
        Duration durationBooking = bookingService.getDuration(getAvailableTimeRequest.getBooking());
        Duration intervalToOrder = templateServiceService.findById(getAvailableTimeRequest.getBooking().getBookedServices().get(0).getId()).getIntervalToOrder();
        return new AvailableTimeResponse(durationBooking.toMinutes(), intervalToOrder.toMinutes(), bookingService.findAvailableDateTimeForBooking(getAvailableTimeRequest.getBooking(), durationBooking, intervalToOrder, allAvailableDateTime)
                .stream()
                .map(adt -> adt.format(DateTimeFormatter.ofPattern(stamp)))
                .collect(Collectors.toList()));
    }
}
