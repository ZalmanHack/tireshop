package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.BookedService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedServiceRepo extends CrudRepository<BookedService, Long> {
}
