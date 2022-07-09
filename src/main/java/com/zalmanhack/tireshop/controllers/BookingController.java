package com.zalmanhack.tireshop.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.zalmanhack.tireshop.domains.*;
import com.zalmanhack.tireshop.domains.enums.OrderStatus;
import com.zalmanhack.tireshop.dtos.BookingDto;
import com.zalmanhack.tireshop.dtos.requests.CreateBookingRequest;
import com.zalmanhack.tireshop.dtos.requests.GetAvailableTimeRequest;
import com.zalmanhack.tireshop.dtos.responses.AvailableTimeResponse;
import com.zalmanhack.tireshop.dtos.responses.CreateBookingResponse;
import com.zalmanhack.tireshop.exceptions.NotAvailableDateTimeException;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @PutMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateBookingResponse add(@Valid @RequestBody CreateBookingRequest createBookingRequest) {
        BookingDto bookingDto = createBookingRequest.getBooking();
        Duration durationBooking = bookingService.getDuration(createBookingRequest.getBooking());
        LocalDateTime appointmentDate = modelMapper.map(createBookingRequest.getAppointmentDate(), LocalDateTime.class);
        List<LocalDateTime> availableDateTimes = bookingService.getAvailableTimesForBooking(bookingDto, durationBooking, appointmentDate);
        if (!availableDateTimes.contains(appointmentDate)) {
            throw new NotAvailableDateTimeException(appointmentDate, stamp);
        }
        User user = userService.findById(bookingDto.getUserId());
        Car car = carService.findById(bookingDto.getCarId());
        Booking booking = bookingService.create(user, car, bookingDto, modelMapper.map(createBookingRequest.getAppointmentDate(), LocalDateTime.class));
        return new CreateBookingResponse(booking, stamp);
    }

    //TODO добавить документацию
    @PostMapping("/available-time")
    public AvailableTimeResponse getAvailableTime(@Valid @RequestBody GetAvailableTimeRequest getAvailableTimeRequest) {
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.plusDays(getAvailableTimeRequest.getRangeDays() - 1);
        Duration durationBooking = bookingService.getDuration(getAvailableTimeRequest.getBooking());
        List<LocalDateTime> resultDateTimes = bookingService.getAvailableTimesForBooking(getAvailableTimeRequest.getBooking(), durationBooking,currentDate, endDate);
        if (resultDateTimes.isEmpty()) {
            throw new NotAvailableDateTimeException(currentDate, endDate);
        }
        return new AvailableTimeResponse(durationBooking.toMinutes(), resultDateTimes
                .stream()
                .map(adt -> adt.format(DateTimeFormatter.ofPattern(stamp)))
                .collect(Collectors.toList()));
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<Object> accept(@PathVariable long id) {
        bookingService.setOrderStatus(bookingService.findById(id), OrderStatus.ACTIVE);
        Map<String, String> response = new HashMap<>();
        response.put(OrderStatus.class.getSimpleName(), OrderStatus.ACTIVE.name());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Object> cancel(@PathVariable long id) {
        bookingService.setOrderStatus(bookingService.findById(id), OrderStatus.CANCELED);
        Map<String, String> response = new HashMap<>();
        response.put(OrderStatus.class.getSimpleName(), OrderStatus.CANCELED.name());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<Object> close(@PathVariable long id) {
        bookingService.setOrderStatus(bookingService.findById(id), OrderStatus.CLOSED);
        Map<String, String> response = new HashMap<>();
        response.put(OrderStatus.class.getSimpleName(), OrderStatus.CLOSED.name());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Object> start(@PathVariable long id) {
        bookingService.setOrderStatus(bookingService.findById(id), OrderStatus.IN_PROGRESS);
        Map<String, String> response = new HashMap<>();
        response.put(OrderStatus.class.getSimpleName(), OrderStatus.IN_PROGRESS.name());
        return ResponseEntity.ok(response);
    }
}
