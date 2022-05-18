package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.Booking;
import com.zalmanhack.tireshop.domains.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepo extends CrudRepository<Booking, Long> {
    long countBookingsByCar(Car car);
}
