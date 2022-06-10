package com.zalmanhack.tireshop.utils;

import com.zalmanhack.tireshop.domains.Booking;
import lombok.Getter;
import lombok.Value;

import java.util.Iterator;
import java.util.List;

public class BookingUtil {

    private final Iterator<Booking> bookingCompositeIterator;
    private final Iterator<Booking> bookingNotCompositeIterator;

    public BookingUtil(List<Booking> bookings) {
        bookingCompositeIterator = bookings.listIterator();
        bookingNotCompositeIterator = bookings.listIterator();
    }

    private static Booking nextBooking(Iterator<Booking> iterator, boolean isComposite) {
        while (iterator.hasNext()) {
            Booking currentBooking = iterator.next();
            if(isComposite && currentBooking.getBookedServices().get(0).isComposite()) {
                return currentBooking;
            }
            else if (!isComposite && !currentBooking.getBookedServices().get(0).isComposite()) {
                return currentBooking;
            }
        }
        return null;
    }

    public Booking nextComposite() {
        return nextBooking(bookingCompositeIterator, true);
    }

    public Booking nextNotComposite() {
        return nextBooking(bookingNotCompositeIterator, false);
    }
}
