package com.zalmanhack.tireshop.utils;

import com.zalmanhack.tireshop.domains.Booking;
import com.zalmanhack.tireshop.domains.Timetable;

import java.util.Iterator;

public class TimetableUtil {

    private final Iterator<Timetable> timetableIterator;

    public TimetableUtil(Iterator<Timetable> timetableIterator) {
        this.timetableIterator = timetableIterator;
    }
}
