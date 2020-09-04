package com.mybank.customer.service.helpers;

import com.mybank.customer.exception.ValidationException;
import com.mybank.customer.model.CustomerDetails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RequestValidationHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestValidationHelper.class);

    public boolean validateRegisterRequest(CustomerDetails customerDetails) throws ValidationException {
        LOGGER.info("Register Request - Validating request payload- user: {}",
                null!=customerDetails? customerDetails.getUserName(): null);
        if(StringUtils.isBlank(customerDetails.getEmailId())){
            LOGGER.warn("Register Request - Validation failed- emailId not given.");
            throw new ValidationException(
                    ValidationException.ValidationError.EMAILID_IS_MANDATORY);
        }
        if(StringUtils.isBlank(customerDetails.getLastName())){
            LOGGER.warn("Register Request - Validation failed- lastName not given.");
            throw new ValidationException(
                    ValidationException.ValidationError.LASTNAME_IS_MANDATORY);
        }
        if(StringUtils.isBlank(customerDetails.getMobileNumber())){
            LOGGER.warn("Register Request - Validation failed- mobileNumber not given.");
            throw new ValidationException(
                    ValidationException.ValidationError.MOBILENUMBER_IS_MANDATORY);
        }
        LOGGER.info("Register Request - validation success for user: {}", customerDetails.getUserName());
        return true;

    }
}
