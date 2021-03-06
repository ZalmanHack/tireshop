package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.zalmanhack.tireshop.domains.enums.OrderStatus;
import com.zalmanhack.tireshop.utils.converters.DurationConverter;
import com.zalmanhack.tireshop.views.BookingView;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@ToString(includeFieldNames=true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = BookingView.Public.class)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @JsonView(value = BookingView.Public.class)
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    private User user;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonView(value = BookingView.Public.class)
    private LocalDateTime registrationDate;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonView(value = BookingView.Public.class)
    private LocalDateTime appointmentDate;

    @Column(columnDefinition = "TIMESTAMP")
    @JsonView(value = BookingView.Public.class)
    private LocalDateTime closedDate;

    @OneToMany(mappedBy = "booking",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<BookedService> bookedServices;

    @JsonView(value = BookingView.Public.class)
    private OrderStatus orderStatus;

    @Convert(converter = DurationConverter.class)
    private Duration duration = Duration.ZERO;

    @NotNull
    @Min(0)
    @JsonView(value = BookingView.Public.class)
    private long price;

    @PrePersist
    private void init() {
        registrationDate = LocalDateTime.now();
    }
}
