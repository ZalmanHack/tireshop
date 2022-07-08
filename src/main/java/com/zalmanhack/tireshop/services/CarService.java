package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.Car;
import com.zalmanhack.tireshop.domains.Color;
import com.zalmanhack.tireshop.domains.User;
import com.zalmanhack.tireshop.exceptions.RecordMaxCountException;
import com.zalmanhack.tireshop.exceptions.RecordNotFoundException;
import com.zalmanhack.tireshop.repos.CarRepo;
import com.zalmanhack.tireshop.utils.TransactionHandler;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Getter
    @Value("${entity.car.max-count}")
    private long maxCount;

    private final TransactionHandler transactionHandler;
    private final CarRepo carRepo;
    private final BookingService bookingService;

    @Autowired
    public CarService(TransactionHandler transactionHandler, CarRepo carRepo, BookingService bookingService) {
        this.transactionHandler = transactionHandler;
        this.carRepo = carRepo;
        this.bookingService = bookingService;
    }

    public List<Car> findAllByUser(User user, Boolean removed) {
        Optional<List<Car>> optionalCars = removed == null ?
                carRepo.findAllByUser(user) :
                carRepo.findByUserAndRemoved(user, removed);
        if(!optionalCars.isPresent()) {
            throw new RecordNotFoundException(Car.class);
        }
        return optionalCars.get();
    }

    public Car findById(Long id) {
        Optional<Car> optionalCar = carRepo.findById(id);
        if(!optionalCar.isPresent()) {
            throw new RecordNotFoundException(Car.class, id);
        }
        return optionalCar.get();
    }

    public Car create(Car car, User user, Color color) {
        System.out.println(transactionHandler.runInTransaction(() -> carRepo.countByUserIdAndRemovedIsFalse(user.getId())));
        if(transactionHandler.runInTransaction(() -> carRepo.countByUserIdAndRemovedIsFalse(user.getId()) >= this.getMaxCount())) {
            throw new RecordMaxCountException(Car.class, this.getMaxCount());
        }
        car.setColor(color);
        car.setUser(user);
        return transactionHandler.runInTransaction(() -> carRepo.save(car));
    }

    public void remove(long id) {
        Optional<Car> carFromDb = transactionHandler.runInTransaction(() -> carRepo.findById(id));
        if(!carFromDb.isPresent()) {
            throw new RecordNotFoundException(Car.class, id);
        }
        Car car = carFromDb.get();
        if(bookingService.isCarHasBookings(car)) {
            // если car найдена, но имеет уже заказы, то ее не удаляем, а помечаем, как удаленную.
            // При попытке удалить ее еще раз будет такая же ошибка, как и при отсутствии записи
            if(car.isRemoved()) {
                throw new RecordNotFoundException(Car.class, id);
            }
            car.setRemoved(true);
            transactionHandler.runInTransaction(() -> carRepo.save(car));
        } else {
            carRepo.delete(car);
        }
    }
}
