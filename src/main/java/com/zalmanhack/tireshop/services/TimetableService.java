package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.Timetable;
import com.zalmanhack.tireshop.repos.TimetableRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO При добавлении нового изменения в расписании перезаписывать существующую запись.
// На 1 день она может быть только одна

@Service
public class TimetableService {

    private final TimetableRepo timetableRepo;

    public TimetableService(TimetableRepo timetableRepo) {
        this.timetableRepo = timetableRepo;
    }

    @Transactional
    public List<Timetable> findTimetableChanges(LocalDate start, LocalDate end) {
        List<Timetable> result = new ArrayList<>();
        timetableRepo.findFirstByChangedWorkTimeDateBeforeOrderByChangedWorkTimeDateDesc(start).ifPresent(result::add);
        result.addAll(timetableRepo.findByChangedWorkTimeDateGreaterThanEqualAndChangedWorkTimeDateLessThanEqualOrderByChangedWorkTimeDateAsc(start, end));
        return result;
    }
}
