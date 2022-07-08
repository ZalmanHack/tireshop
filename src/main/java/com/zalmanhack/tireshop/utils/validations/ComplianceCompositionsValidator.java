package com.zalmanhack.tireshop.utils.validations;

import com.zalmanhack.tireshop.dtos.BookedServiceDto;
import com.zalmanhack.tireshop.dtos.BookingDto;
import com.zalmanhack.tireshop.dtos.requests.AbstractBookingRequest;
import com.zalmanhack.tireshop.dtos.requests.GetAvailableTimeRequest;
import com.zalmanhack.tireshop.services.TemplateServiceService;
import com.zalmanhack.tireshop.utils.TransactionHandler;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//TODO разобрать с тем, что использовать, компонент или конфигуратор
@Component
@Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)
public class ComplianceCompositionsValidator implements ConstraintValidator<ComplianceCompositions, AbstractBookingRequest> {

    private final TemplateServiceService templateServiceService;

    public ComplianceCompositionsValidator(TemplateServiceService templateServiceService) {
        this.templateServiceService = templateServiceService;
    }

    @Override
    public void initialize(ComplianceCompositions constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(AbstractBookingRequest abstractBookingRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (abstractBookingRequest == null) {
            return false;
        }

        BookingDto booking = abstractBookingRequest.getBooking();
        if (booking == null || booking.getBookedServices() == null || booking.getBookedServices().size() == 0) {
            return false;
        }
        if (abstractBookingRequest.getComposite() && booking.getBookedServices().size() > 1) {
            return false;
        }

        for (int i = 0; i < booking.getBookedServices().size(); i++) {
            BookedServiceDto bookedServiceDto = booking.getBookedServices().get(i);
            if (templateServiceService.findById(bookedServiceDto.getId()).isComposite()) {
                return false;
            }
        }
        return true;
    }
}
