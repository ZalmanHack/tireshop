package com.zalmanhack.tireshop.dtos;

import com.zalmanhack.tireshop.domains.Booking;
import com.zalmanhack.tireshop.domains.Car;
import com.zalmanhack.tireshop.domains.enums.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private long id;

    @NotBlank
    @Size(min = 1, max = 64)
    private String username;

    @NotBlank
    @Size(min = 1, max = 26)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 26)
    private String lastName;

    @NotNull
    @Min(100)
    private long phone;

    @NotBlank
    @Size(min = 5, max = 320)
    private String email;

    @Size(max = 320)
    private String newEmail;

    private List<CarDto> cars;

    private List<BookingDto> bookings;

    private short rating;

    private String password = "";

    private String activationCode;

    @NotNull
    private Boolean active;

    private Set<Role> roles;
}
