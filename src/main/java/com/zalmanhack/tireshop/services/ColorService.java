package com.zalmanhack.tireshop.services;

import com.zalmanhack.tireshop.domains.Color;
import com.zalmanhack.tireshop.exceptions.RecordNotFoundException;
import com.zalmanhack.tireshop.repos.ColorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColorService {

    private final ColorRepo colorRepo;

    @Autowired
    public ColorService(ColorRepo colorRepo) {
        this.colorRepo = colorRepo;
    }


    public Color findById(Long id) {
        Optional<Color> optionalColor = colorRepo.findById(id);
        if(!optionalColor.isPresent()) {
            throw new RecordNotFoundException(Color.class, id);
        }
        return optionalColor.get();
    }
}
