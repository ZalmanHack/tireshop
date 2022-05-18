package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.User;
import com.zalmanhack.tireshop.exceptions.RecordNotFoundException;
import com.zalmanhack.tireshop.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findById(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if(!optionalUser.isPresent()) {
            throw new RecordNotFoundException(User.class, id);
        }
        return optionalUser.get();
    }
}
