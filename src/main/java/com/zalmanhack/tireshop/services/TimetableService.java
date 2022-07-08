package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.Timetable;
import com.zalmanhack.tireshop.repos.TimetableRepo;
import com.zalmanhack.tireshop.utils.TransactionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TODO При добавлении нового изменения в расписании перезаписывать существующую запись.
// На 1 день она может быть только одна

@Service
public class TimetableService {

    private final TransactionHandler transactionHandler;
    private final TimetableRepo timetableRepo;

    public TimetableService(TransactionHandler transactionHandler, TimetableRepo timetableRepo) {
        this.transactionHandler = transactionHandler;
        this.timetableRepo = timetableRepo;
    }

    @Transactional
    public List<Timetable> findTimetableChanges(LocalDate start, LocalDate end) {
        List<Timetable> result = new ArrayList<>();
        timetableRepo.findFirstByChangedWorkTimeDateBeforeOrderByChangedWorkTimeDateDesc(start).ifPresent(result::add);
        result.addAll(transactionHandler.runInTransaction(() -> timetableRepo.findByChangedWorkTimeDateGreaterThanEqualAndChangedWorkTimeDateLessThanEqualOrderByChangedWorkTimeDateAsc(start, end)));
        return result;
    }

}
