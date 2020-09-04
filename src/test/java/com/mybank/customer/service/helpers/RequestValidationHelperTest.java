package com.mybank.customer.service.helpers;

import com.mybank.customer.exception.ValidationException;
import com.mybank.customer.model.CustomerDetails;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RequestValidationHelperTest {

    @InjectMocks
    RequestValidationHelper requestValidationHelper;

    CustomerDetails customerDetails;

    @Before
    public void setUp() {
        customerDetails = new CustomerDetails();
        customerDetails.setEmailId("emailId");
        customerDetails.setMobileNumber("22333");
        customerDetails.setLastName("LName");
    }

    @Test
    public void validateRegisterRequest_success() throws ValidationException {
        Assert.assertTrue(requestValidationHelper.validateRegisterRequest(customerDetails));
    }

    @Test(expected = ValidationException.class)
    public void validateRegisterRequest_email_null() throws ValidationException {
        customerDetails.setEmailId(null);
        requestValidationHelper.validateRegisterRequest(customerDetails);
    }

    @Test(expected = ValidationException.class)
    public void validateRegisterRequest_email_blank() throws ValidationException {
        customerDetails.setEmailId(" ");
        requestValidationHelper.validateRegisterRequest(customerDetails);
    }

    @Test(expected = ValidationException.class)
    public void validateRegisterRequest_lastName_null() throws ValidationException {
        customerDetails.setLastName(null);
        requestValidationHelper.validateRegisterRequest(customerDetails);
    }

    @Test(expected = ValidationException.class)
    public void validateRegisterRequest_lastName_blank() throws ValidationException {
        customerDetails.setLastName(" ");
        requestValidationHelper.validateRegisterRequest(customerDetails);
    }

    @Test(expected = ValidationException.class)
    public void validateRegisterRequest_mobileNumber_null() throws ValidationException {
        customerDetails.setMobileNumber(null);
        requestValidationHelper.validateRegisterRequest(customerDetails);
    }

    @Test(expected = ValidationException.class)
    public void validateRegisterRequest_mobileNumber_blank() throws ValidationException {
        customerDetails.setMobileNumber(" ");
        requestValidationHelper.validateRegisterRequest(customerDetails);
    }

}