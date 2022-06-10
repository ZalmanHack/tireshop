package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.Booking;
import com.zalmanhack.tireshop.domains.Car;
import com.zalmanhack.tireshop.domains.Timetable;
import com.zalmanhack.tireshop.repos.BookingRepo;
import com.zalmanhack.tireshop.utils.BookingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BookingService {

    @Value("${entity.car.max-count}")
    private long maxCount;

    @Value("${timetable.slot-duration}")
    private short slotDuration;

    private final BookingRepo bookingRepo;
    private final WorkWeekService workWeekService;

    @Autowired
    public BookingService(BookingRepo bookingRepo, WorkWeekService workWeekService) {
        this.bookingRepo = bookingRepo;
        this.workWeekService = workWeekService;
    }

    public boolean isCarHasBookings(Car car) {
        return bookingRepo.countBookingsByCar(car) != 0;
    }

    public List<LocalDateTime> findAvailableDateTimeForDate(Timetable timetable, @NotNull List<Booking> bookings, LocalDate currentDate, boolean composite) {
        List<LocalDateTime> availableDateTimes = new ArrayList<>();
        LocalDateTime currentTime = currentDate.atTime(timetable.getTimeOfOpen());
        BookingUtil bookingUtil = new BookingUtil(bookings);
        Booking currentComposite = bookingUtil.nextComposite();
        Booking currentNotComposite = bookingUtil.nextNotComposite();

        while (currentTime.isBefore(currentDate.atTime(timetable.getTimeOfClose()))) {
            if (currentTime.equals(currentDate.atTime(timetable.getTimeOfBreakStart()))) {
                currentTime = currentTime.plusMinutes(ChronoUnit.MINUTES.between(currentTime, currentDate.atTime(timetable.getTimeOfBreakEnd())));
            }

            if (currentNotComposite != null && currentTime.isAfter(currentNotComposite.getAppointmentDate()) && currentTime.isBefore(currentNotComposite.getClosedDate())) {
                if (composite) {
                    if (currentComposite != null && currentTime.equals(currentComposite.getAppointmentDate())) {
                        currentComposite = bookingUtil.nextComposite();
                    } else {
                        availableDateTimes.add(currentTime);
                        System.out.println(currentTime);
                    }
                }
            }
            else if (currentNotComposite != null && currentTime.equals(currentNotComposite.getClosedDate())) {
                currentNotComposite = bookingUtil.nextNotComposite();
                if (currentNotComposite == null || !currentTime.equals(currentNotComposite.getAppointmentDate())) {
                    availableDateTimes.add(currentTime);
                    System.out.println(currentTime);
                }
            }
            else if (currentNotComposite == null || (currentComposite != null && !currentTime.equals(currentNotComposite.getAppointmentDate()) && !currentTime.equals(currentComposite.getAppointmentDate()))) {
                availableDateTimes.add(currentTime);
                System.out.println(currentTime);
            }

            if (currentComposite != null && currentTime.equals(currentComposite.getAppointmentDate())) {
                currentComposite = bookingUtil.nextComposite();
            }

            currentTime = currentTime.plusMinutes(slotDuration);
        }
        return availableDateTimes;
    }

    public List<LocalDateTime> findAvailableDateTimeForRangeDays(List<Timetable> timetableChanges, short range, boolean composite) {
        Timetable currentTimetable = timetableChanges.get(0);
        LocalDateTime currentDate = LocalDateTime.of(LocalDate.of(2022,6,7),LocalTime.MIDNIGHT);
        LocalDateTime endDate = currentDate.plusDays(range);
        List<Booking> allRangeBookings = bookingRepo.findByAppointmentDateGreaterThanEqualAndAndAppointmentDateLessThanEqualOrderByAppointmentDate(currentDate, endDate);
        return findAvailableDateTimeForDate(currentTimetable, allRangeBookings, currentDate.toLocalDate(), composite);
    }


}
