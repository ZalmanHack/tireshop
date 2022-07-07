package com.zalmanhack.tireshop.utils;

import com.zalmanhack.tireshop.domains.Booking;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public class BookingUtil {

    private final Iterator<Booking> bookingCompositeIterator;
    private final Iterator<Booking> bookingNotCompositeIterator;

    @Getter
    private Booking composite = null;
    @Getter
    private Booking notComposite = null;

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

    public void nextComposite() {
        composite = nextBooking(bookingCompositeIterator, true);
    }

    public void nextNotComposite() {
        notComposite = nextBooking(bookingNotCompositeIterator, false);
    }

    public static boolean isInsideBooking(Booking booking, LocalDateTime dateTime) {
        return booking != null && booking.getAppointmentDate().isBefore(dateTime) && booking.getClosedDate().isAfter(dateTime);
    }

    public static boolean isStartBooking(Booking booking, LocalDateTime dateTime) {
        return booking != null && booking.getAppointmentDate().equals(dateTime);
    }

    public static boolean isEndBooking (Booking booking, LocalDateTime dateTime) {
        return booking != null && booking.getClosedDate().equals(dateTime);
    }

    public static boolean isOutsideBooking(Booking booking, LocalDateTime dateTime) {
        return booking == null || booking.getAppointmentDate().isAfter(dateTime) || booking.getClosedDate().isBefore(dateTime);
    }
}
