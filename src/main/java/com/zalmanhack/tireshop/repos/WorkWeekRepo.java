package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.WorkWeek;
import com.zalmanhack.tireshop.domains.enums.Week;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkWeekRepo extends CrudRepository<WorkWeek, Long> {
    boolean existsByWeekDay(Week weekDay);

    WorkWeek findByWeekDay(Week weekDay);
}
