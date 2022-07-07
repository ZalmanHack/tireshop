package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.BookedValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedValueRepo extends CrudRepository<BookedValue, Long> {

}
