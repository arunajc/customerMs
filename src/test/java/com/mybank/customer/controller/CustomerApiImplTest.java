package com.mybank.customer.controller;

import com.mybank.customer.exception.AuthenticationException;
import com.mybank.customer.exception.CustomerRegistrationException;
import com.mybank.customer.exception.GeneralException;
import com.mybank.customer.exception.ValidationException;
import com.mybank.customer.model.CustomerDetails;
import com.mybank.customer.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CustomerApiImplTest {

    @InjectMocks
    CustomerApiImpl customerApi = new CustomerApiImpl();

    @Mock
    CustomerService customerService;

    CustomerDetails customerDetails;

    @Before
    public void setUp() {
        customerApi.customerService = customerService;

        customerDetails = new CustomerDetails();
        customerDetails.setUserName("cust1");
        customerDetails.setPassword("cust1pass");
    }

    @Test
    public void login_success() throws AuthenticationException, GeneralException {

        Mockito.when(customerService.login(customerDetails)).thenReturn(customerDetails);

        Object response = customerApi.login(customerDetails);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.OK, ((ResponseEntity)response).getStatusCode());
        assertThat(((ResponseEntity)response).getBody(), instanceOf(CustomerDetails.class));
        CustomerDetails custDetailsResponse =  (CustomerDetails) ((ResponseEntity)response).getBody();
        assertEquals("cust1", custDetailsResponse.getUserName());
        assertEquals("cust1pass", custDetailsResponse.getPassword());
    }

    @Test(expected = AuthenticationException.class)
    public void login_AuthenticationException() throws AuthenticationException, GeneralException {

        Mockito
                .doThrow(new AuthenticationException(AuthenticationException.AuthenticationError.AUTHENTICATION_FAILED))
                .when(customerService).login(customerDetails);

        customerApi.login(customerDetails);
    }

    @Test(expected = GeneralException.class)
    public void login_GeneralException() throws AuthenticationException, GeneralException {

        Mockito
                .doThrow(new GeneralException(GeneralException.GeneralError.UNEXPECTED_ERROR))
                .when(customerService).login(customerDetails);

        customerApi.login(customerDetails);
    }

    @Test
    public void register_success()
            throws GeneralException, CustomerRegistrationException, ValidationException {

        customerDetails.setEmailId("test@tester.com");
        customerDetails.setMobileNumber("1234567890");
        customerDetails.setLastName("LastName");
        Mockito.when(customerService.register(customerDetails)).thenReturn(customerDetails);

        Object response = customerApi.register(customerDetails);

        assertThat(response, instanceOf(ResponseEntity.class));
        assertEquals(HttpStatus.OK, ((ResponseEntity)response).getStatusCode());
        assertThat(((ResponseEntity)response).getBody(), instanceOf(CustomerDetails.class));
        CustomerDetails custDetailsResponse =  (CustomerDetails) ((ResponseEntity)response).getBody();
        assertEquals("cust1", custDetailsResponse.getUserName());
        assertEquals("cust1pass", custDetailsResponse.getPassword());
        assertEquals("test@tester.com", custDetailsResponse.getEmailId());
        assertEquals("1234567890", custDetailsResponse.getMobileNumber());
        assertEquals("LastName", custDetailsResponse.getLastName());
    }

    @Test(expected = GeneralException.class)
    public void register_GeneralException()
            throws GeneralException, CustomerRegistrationException, ValidationException {

        customerDetails.setEmailId("test@tester.com");
        customerDetails.setMobileNumber("1234567890");
        customerDetails.setLastName("LastName");

        Mockito
                .doThrow(new GeneralException(GeneralException.GeneralError.UNEXPECTED_ERROR))
                .when(customerService).register(customerDetails);

        customerApi.register(customerDetails);
    }

    @Test(expected = CustomerRegistrationException.class)
    public void register_CustomerRegistrationException()
            throws GeneralException, CustomerRegistrationException, ValidationException {

        customerDetails.setEmailId("test@tester.com");
        customerDetails.setMobileNumber("1234567890");
        customerDetails.setLastName("LastName");

        Mockito
                .doThrow(new CustomerRegistrationException(CustomerRegistrationException.CustomerRegistrationError.CUSTOMER_ALREADY_EXISTS_EMAIL))
                .when(customerService).register(customerDetails);

        customerApi.register(customerDetails);
    }

    @Test(expected = ValidationException.class)
    public void register_ValidationException()
            throws GeneralException, CustomerRegistrationException, ValidationException {

        customerDetails.setEmailId("test@tester.com");
        customerDetails.setMobileNumber("1234567890");
        customerDetails.setLastName("LastName");

        Mockito
                .doThrow(new ValidationException(ValidationException.ValidationError.MOBILENUMBER_IS_MANDATORY))
                .when(customerService).register(customerDetails);

        customerApi.register(customerDetails);
    }

}