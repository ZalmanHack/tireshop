package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zalmanhack.tireshop.domains.abstracts.AbstractService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookedService extends AbstractService {

    @OneToMany(mappedBy = "bookedService",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<BookedOption> bookedOptions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dateOfStartWork;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dateOfEndWork;
}
