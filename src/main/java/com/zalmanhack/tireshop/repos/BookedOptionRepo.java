package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.BookedOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedOptionRepo extends CrudRepository<BookedOption, Long> {
}
