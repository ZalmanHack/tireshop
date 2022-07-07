package com.zalmanhack.tireshop.repos;

import com.zalmanhack.tireshop.domains.RefreshToken;
import com.zalmanhack.tireshop.domains.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    @Modifying
    int deleteByUser(User user);
}
