package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zalmanhack.tireshop.domains.enums.OrderStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@ToString(includeFieldNames=true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    private User user;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime registrationDate;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime appointmentDate;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime closedDate;

    @OneToMany(mappedBy = "booking",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<BookedService> bookedServices;

    private OrderStatus orderStatus;

    @NotNull
    private long price;
}
