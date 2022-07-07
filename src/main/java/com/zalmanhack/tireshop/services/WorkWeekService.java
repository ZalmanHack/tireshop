package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.WorkWeek;
import com.zalmanhack.tireshop.domains.enums.Week;
import com.zalmanhack.tireshop.repos.WorkWeekRepo;
import org.springframework.stereotype.Service;

@Service
public class WorkWeekService {

    private final WorkWeekRepo workWeekRepo;

    public WorkWeekService(WorkWeekRepo workWeekRepo) {
        this.workWeekRepo = workWeekRepo;
    }

    public boolean isWorkingDay(Week value) {
        return workWeekRepo.findByWeekDay(value).isWorking();
    }
}
