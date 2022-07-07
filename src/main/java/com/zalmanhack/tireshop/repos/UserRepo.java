package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
