package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.Car;
import com.zalmanhack.tireshop.domains.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepo extends CrudRepository<Car, Long>{
    Optional<List<Car>> findAllByUser(User user);
    long countByUserIdAndRemovedIsFalse(long id);
}
