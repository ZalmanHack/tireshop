package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.*;
import com.zalmanhack.tireshop.domains.enums.OrderStatus;
import com.zalmanhack.tireshop.domains.enums.Week;
import com.zalmanhack.tireshop.dtos.BookedOptionDto;
import com.zalmanhack.tireshop.dtos.BookedServiceDto;
import com.zalmanhack.tireshop.dtos.BookingDto;
import com.zalmanhack.tireshop.repos.BookingRepo;
import com.zalmanhack.tireshop.utils.BookingUtil;
import com.zalmanhack.tireshop.utils.TransactionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Value("${entity.car.max-count}")
    private long maxCount;

    @Value("${timetable.slot-duration}")
    private short slotDuration;

    private final TransactionHandler transactionHandler;
    private final ModelMapper modelMapper;
    private final BookingRepo bookingRepo;
    private final WorkWeekService workWeekService;
    private final BookedServiceService bookedServiceService;
    private final TemplateValueService templateValueService;
    private final TemplateServiceService templateServiceService;


    @Autowired
    public BookingService(TransactionHandler transactionHandler, ModelMapper modelMapper, BookingRepo bookingRepo, WorkWeekService workWeekService, BookedServiceService bookedServiceService, TemplateValueService templateValueService, TemplateServiceService templateServiceService) {
        this.transactionHandler = transactionHandler;
        this.modelMapper = modelMapper;
        this.bookingRepo = bookingRepo;
        this.workWeekService = workWeekService;
        this.bookedServiceService = bookedServiceService;
        this.templateValueService = templateValueService;
        this.templateServiceService = templateServiceService;
    }

    public boolean isCarHasBookings(Car car) {
        return bookingRepo.countBookingsByCar(car) != 0;
    }

    public List<LocalDateTime> findAvailableDateTimeForDate(Timetable timetable, List<Booking> bookings, LocalDate currentDate, boolean composite) {
        List<LocalDateTime> availableDateTimes = new ArrayList<>();
        LocalDateTime currentTime = currentDate.atTime(timetable.getTimeOfOpen());
        BookingUtil bookingUtil = new BookingUtil(bookings);
        bookingUtil.nextComposite();
        bookingUtil.nextNotComposite();

        while (currentTime.isBefore(currentDate.atTime(timetable.getTimeOfClose()))) {
            if (currentTime.equals(currentDate.atTime(timetable.getTimeOfBreakStart()))) {
                if (bookingUtil.getNotComposite() != null && bookingUtil.getNotComposite().getClosedDate().equals(currentTime)) {
                    bookingUtil.nextNotComposite();
                }
                currentTime = currentTime.plusMinutes(ChronoUnit.MINUTES.between(currentTime,
                        currentDate.atTime(timetable.getTimeOfBreakEnd())));
            }
            if (BookingUtil.isOutsideBooking(bookingUtil.getNotComposite(), currentTime) &&
                    !BookingUtil.isStartBooking(bookingUtil.getComposite(), currentTime)) {
                availableDateTimes.add(currentTime);
            } else {
                if (BookingUtil.isInsideBooking(bookingUtil.getNotComposite(), currentTime)) {
                    if (composite) {
                        if (bookingUtil.getComposite() == null) {
                            availableDateTimes.add(currentTime);
                        }
                        else if (!BookingUtil.isStartBooking(bookingUtil.getComposite(), currentTime)) {
                            availableDateTimes.add(currentTime);
                        }
                    }
                }
                if (BookingUtil.isEndBooking(bookingUtil.getNotComposite(), currentTime)) {
                    bookingUtil.nextNotComposite();
                    if (!BookingUtil.isStartBooking(bookingUtil.getNotComposite(), currentTime) &&
                            !BookingUtil.isStartBooking(bookingUtil.getComposite(), currentTime)) {
                        availableDateTimes.add(currentTime);
                    }
                }
                if (BookingUtil.isEndBooking(bookingUtil.getComposite(), currentTime)) {
                    bookingUtil.nextComposite();
                }
            }
            currentTime = currentTime.plusMinutes(slotDuration);
        }
        return availableDateTimes;
    }

    public List<LocalDateTime> findAvailableDateTimeForRangeDates(List<Timetable> timetableChanges, LocalDate currentDate, LocalDate endDate, boolean composite) {
        if (timetableChanges == null || timetableChanges.size() == 0) {
            throw new NullPointerException("No records found in the timetable");
        }

        Iterator<Timetable> timetableIterator = timetableChanges.listIterator();
        Timetable currentTimetable = timetableIterator.next();
        Timetable nextTimetable = null;
        if (timetableIterator.hasNext()) {
            nextTimetable = timetableIterator.next();
        }

        LocalDate finalCurrentDate = currentDate;
        List<Booking> allRangeBookings = transactionHandler.runInTransaction(() -> bookingRepo.findByAppointmentDateGreaterThanEqualAndAndAppointmentDateLessThanEqualOrderByAppointmentDate(finalCurrentDate.atTime(LocalTime.MIDNIGHT), endDate.atTime(LocalTime.MIDNIGHT)));
        int fromIndexBookings = 0;
        int toIndexBooking = 0;

        List<LocalDateTime> result = new ArrayList<>();

        while (currentDate.isBefore(endDate) || currentDate.equals(endDate)) {
            if (nextTimetable != null && currentDate.equals(nextTimetable.getChangedWorkTimeDate())) {
                currentTimetable = nextTimetable;
                if (timetableIterator.hasNext()) {
                    nextTimetable = timetableIterator.next();
                }
            }

            if (workWeekService.isWorkingDay(Week.values()[currentDate.getDayOfWeek().getValue() - 1])) {
                for ( ; toIndexBooking < allRangeBookings.size(); toIndexBooking++) {
                    if (!allRangeBookings.get(toIndexBooking).getAppointmentDate().toLocalDate().equals(currentDate)) {
                        break;
                    }
                }
                result.addAll(findAvailableDateTimeForDate(currentTimetable, allRangeBookings.subList(fromIndexBookings, toIndexBooking), currentDate, composite));
                fromIndexBookings = toIndexBooking;
            }

            currentDate = currentDate.plusDays(1);
        }
        return result;
    }



    public Booking create(User user, Car car, BookingDto bookingDto, LocalDateTime appointmentDateTime) {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCar(car);
        booking.setAppointmentDate(appointmentDateTime);
        booking.setOrderStatus(OrderStatus.IN_PROCESSING);
        Booking bookingDb = transactionHandler.runInTransaction(() -> bookingRepo.save(booking));

        LocalDateTime startWork = bookingDb.getAppointmentDate();
        Duration duration = Duration.ZERO;
        long price = 0;

        for (BookedServiceDto bookedServiceDto : bookingDto.getBookedServices()) {
            BookedService bookedServiceDb = bookedServiceService.create(bookingDb, startWork, bookedServiceDto);
            startWork = bookedServiceDb.getDateOfEndWork();
            duration = duration.plus(bookedServiceDb.getDuration());
            price += bookedServiceDb.getPrice();
        }

        bookingDb.setDuration(duration);
        bookingDb.setPrice(price);
        return transactionHandler.runInTransaction(() -> bookingRepo.save(bookingDb));
    }

    public Duration getDuration(BookingDto bookingDto) {
        Duration duration = Duration.ZERO;
        for (BookedServiceDto bookedServiceDto : bookingDto.getBookedServices()) {
            for (BookedOptionDto bookedOptionDto : bookedServiceDto.getBookedOptions()) {
                duration = duration.plus(templateValueService.findById(bookedOptionDto.getBookedValueId()).getDuration());
            }
        }
        return duration;
    }

    public List<LocalDateTime> findAvailableDateTimeForBooking(BookingDto bookingDto, Duration durationBooking, Duration intervalToOrder, List<LocalDateTime> allAvailableDateTime) {

        allAvailableDateTime = this.removeIntervalToOrder(intervalToOrder, allAvailableDateTime);
        List<LocalDateTime> result = new ArrayList<>();

        for (int i = 0; i < allAvailableDateTime.size(); i++) {
            LocalDateTime currentDateTime = allAvailableDateTime.get(i);
            Duration currentDuration = Duration.ofMinutes(slotDuration);
            if (durationBooking.compareTo(currentDuration) == 0) {
                result.add(currentDateTime);
            } else {
                for (int j = i + 1; j < allAvailableDateTime.size(); j++) {
                    LocalDateTime nextDateTime = allAvailableDateTime.get(j);
                    if (Duration.between(currentDateTime, nextDateTime).equals(currentDuration)) {
                        currentDuration = currentDuration.plus(Duration.ofMinutes(slotDuration));
                        if (durationBooking.compareTo(currentDuration) == 0) {
                            result.add(currentDateTime);
                            if (i < allAvailableDateTime.size()) {
                                currentDateTime = allAvailableDateTime.get(++i);
                                currentDuration = currentDuration.minus(Duration.ofMinutes(slotDuration));
                            } else {
                                break;
                            }
                        }
                    }
                    else {
                        break;
                    }
                }
            }
        }
        return result;
    }

    private List<LocalDateTime> removeIntervalToOrder(Duration intervalToOrder, List<LocalDateTime> allAvailableDateTime) {
        LocalDateTime dateTimeStart = LocalDateTime.now().plus(intervalToOrder);
        return allAvailableDateTime.stream().filter(dateTime -> dateTime.isAfter(dateTimeStart)).collect(Collectors.toList());
    }
}
