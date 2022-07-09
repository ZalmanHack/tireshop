package com.zalmanhack.tireshop.utils.validations;

import com.zalmanhack.tireshop.dtos.BookedServiceDto;
import com.zalmanhack.tireshop.dtos.BookingDto;
import com.zalmanhack.tireshop.services.TemplateServiceService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//TODO разобрать с тем, что использовать, компонент или конфигуратор
//@Component
@Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)
public class ComplianceCompositionsValidator implements ConstraintValidator <ComplianceCompositions, BookingDto> {

    private final TemplateServiceService templateServiceService;

    public ComplianceCompositionsValidator(TemplateServiceService templateServiceService) {
        this.templateServiceService = templateServiceService;
    }

    @Override
    public void initialize(ComplianceCompositions constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BookingDto bookingDto, ConstraintValidatorContext constraintValidatorContext) {
        if (bookingDto == null || bookingDto.getBookedServices() == null || bookingDto.getBookedServices().size() == 0) {
            return false;
        }

        for (int i = 0; i < bookingDto.getBookedServices().size(); i++) {
            BookedServiceDto bookedServiceDto = bookingDto.getBookedServices().get(i);
            if (bookingDto.getComposite().equals(templateServiceService.findById(bookedServiceDto.getId()).isComposite())) {
                if (i != 0 && bookingDto.getComposite()) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
