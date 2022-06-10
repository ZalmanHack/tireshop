package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonView;
import com.zalmanhack.tireshop.domains.enums.Week;
import com.zalmanhack.tireshop.views.CarView;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@ToString(includeFieldNames=true)
public class WorkWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = CarView.Internal.class)
    private long id;

    @NotNull
    @Column(length = 9)
    @Enumerated(EnumType.STRING)
    private Week weekDay;

    @NotNull
    private boolean working = true;
}
