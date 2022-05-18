package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@ToString(includeFieldNames=true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime changedWorkTimeDate;

    @Column(columnDefinition = "TIME")
    private LocalTime timeOfOpen;

    @Column(columnDefinition = "TIME")
    private LocalTime timeOfClose;

    @Column(columnDefinition = "TIME")
    private LocalTime TimeOfBreakStart;

    @Column(columnDefinition = "TIME")
    private LocalTime TimeOfBreakEnd;
}
