package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.Car;
import com.zalmanhack.tireshop.repos.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepo bookingRepo;

    @Autowired
    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public boolean isCarHasBookings(Car car) {
        return bookingRepo.countBookingsByCar(car) != 0;
    }
}
