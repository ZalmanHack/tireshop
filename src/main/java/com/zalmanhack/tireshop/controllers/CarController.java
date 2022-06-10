package com.zalmanhack.tireshop.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.zalmanhack.tireshop.domains.Car;
import com.zalmanhack.tireshop.domains.Color;
import com.zalmanhack.tireshop.domains.User;
import com.zalmanhack.tireshop.dtos.CarDto;
import com.zalmanhack.tireshop.services.CarService;
import com.zalmanhack.tireshop.services.ColorService;
import com.zalmanhack.tireshop.services.UserService;
import com.zalmanhack.tireshop.views.CarView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.List;

@RestController
@RequestMapping(value = "/api/car")
public class CarController {

    private final CarService carService;
    private final UserService userService;
    private final ColorService colorService;
    private final ModelMapper modelMapper;


    @Autowired
    public CarController(CarService carService, UserService userService, ColorService colorService, ModelMapper modelMapper) {
        this.carService = carService;
        this.userService = userService;
        this.colorService = colorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/max-count")
    public long getMaxCount() {
        return carService.getMaxCount();
    }

    @GetMapping("/all/{id}")
    @JsonView(value = {CarView.Public.class})
    public List<Car> getAllByUserId(@PathVariable(required = false) Long id,
                                    @Null @RequestParam(name = "removed", required = false) Boolean removed) {
        System.out.println(id);
        return carService.findAllByUser(userService.findById(id), removed);
    }

    @GetMapping("/{id}")
    @JsonView(value = {CarView.Internal.class})
    public Car getById(@PathVariable Long id) {
        return carService.findById(id);
    }

    @JsonView(value = {CarView.Public.class})
    @PutMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Car add(@Valid @RequestBody CarDto carDto) {
        User user = userService.findById(carDto.getUserId());
        Color color = colorService.findById(carDto.getColorId());
        Car car = modelMapper.map(carDto, Car.class);
        return carService.create(car, user, color);
    }

    @DeleteMapping(value = "/remove/{id}")
    public void remove(@PathVariable long id) {
        carService.remove(id);
    }
}
