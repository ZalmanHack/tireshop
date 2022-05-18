package com.zalmanhack.tireshop.controllers;

import com.zalmanhack.tireshop.domains.Car;
import com.zalmanhack.tireshop.domains.Color;
import com.zalmanhack.tireshop.domains.User;
import com.zalmanhack.tireshop.dtos.BookingDto;
import com.zalmanhack.tireshop.dtos.CarDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/booking")
public class BookingController {

    @GetMapping(value = "/get-template")
    public ResponseEntity<Object> getTemplate() {
        return ResponseEntity.ok(new BookingDto());
    }

    @PutMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> add(@Valid @RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingDto);
    }
}
