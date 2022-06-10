package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.Timetable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimetableRepo extends CrudRepository<Timetable, Long> {
    List<Timetable> findByChangedWorkTimeDateGreaterThanEqualAndChangedWorkTimeDateLessThanEqualOrderByChangedWorkTimeDateAsc(LocalDate start, LocalDate end);
    Optional<Timetable> findFirstByChangedWorkTimeDateBeforeOrderByChangedWorkTimeDateDesc(LocalDate date);
    Timetable findFirstByChangedWorkTimeDateAfterOrderByChangedWorkTimeDateAsc(LocalDate date);


}
