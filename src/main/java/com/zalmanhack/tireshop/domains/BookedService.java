package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zalmanhack.tireshop.domains.abstracts.AbstractService;
import com.zalmanhack.tireshop.utils.converters.DurationConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookedService extends AbstractService {

    public BookedService() {
        super();
    }

    public BookedService(AbstractService abstractService) {
        super(abstractService.getName(), abstractService.isComposite(), abstractService.getIntervalToOrder());
    }

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

    @Convert(converter = DurationConverter.class)
    private Duration duration = Duration.ZERO;

    @Min(0)
    private long price;

//    @PrePersist
//    private void init() {
//        Duration duration = Duration.ZERO;
//        for(BookedOption bookedOption : bookedOptions) {
//            duration = duration.plus(bookedOption.getBookedValue().getDuration());
//        }
//        dateOfEndWork = dateOfStartWork.plus(duration);
//    }
}
