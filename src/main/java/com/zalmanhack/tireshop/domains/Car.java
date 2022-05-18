package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.zalmanhack.tireshop.views.CarView;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@ToString(includeFieldNames=true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = CarView.Public.class)
    private long id;

    @NotBlank
    @Column(length = 64)
    @Size(min = 1, max = 64)
    @JsonView(value = CarView.Public.class)
    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    @JsonView(value = CarView.Public.class)
    private Color color;

    @NotBlank
    @Column(length = 12)
    @Size(min = 1, max = 16)
    @JsonView(value = CarView.Public.class)
    private String stateNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    @JsonView(value = CarView.Internal.class)
    private User user;

    // cascade тут не нужен, так как при удалении автомобиля нам не нужно удалять заказ
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "car")
    private List<Booking> bookings;

    @NotNull
    @JsonView(value = CarView.Public.class)
    private double tireDiameter;

    @NotNull
    @JsonView(value = CarView.Internal.class)
    private boolean removed = false;
}
