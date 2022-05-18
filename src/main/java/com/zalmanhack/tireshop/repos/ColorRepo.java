package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.Color;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepo extends CrudRepository<Color, Long> {

}
