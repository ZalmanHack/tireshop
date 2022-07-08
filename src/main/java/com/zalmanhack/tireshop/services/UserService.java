package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.User;
import com.zalmanhack.tireshop.exceptions.RecordNotFoundException;
import com.zalmanhack.tireshop.repos.UserRepo;
import com.zalmanhack.tireshop.utils.TransactionHandler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final TransactionHandler transactionHandler;

    private final UserRepo userRepo;

    public UserService(TransactionHandler transactionHandler, UserRepo userRepo) {
        this.transactionHandler = transactionHandler;
        this.userRepo = userRepo;
    }

    public User findById(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if(!optionalUser.isPresent()) {
            throw new RecordNotFoundException(User.class, id);
        }
        return optionalUser.get();
    }

    public User findByUsername(String username) {
        Optional<User> optionalUser = transactionHandler.runInTransaction(() -> userRepo.findByUsername(username));
        if(!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        return optionalUser.get();
    }
}
