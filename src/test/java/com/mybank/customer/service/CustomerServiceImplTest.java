package com.mybank.customer.service;

import com.mybank.customer.entity.CustomerDetailsEntity;
import com.mybank.customer.exception.AuthenticationException;
import com.mybank.customer.exception.CustomerRegistrationException;
import com.mybank.customer.exception.GeneralException;
import com.mybank.customer.exception.ValidationException;
import com.mybank.customer.model.CustomerDetails;
import com.mybank.customer.repository.CustomerRepository;
import com.mybank.customer.service.helpers.DTOToEntityTransform;
import com.mybank.customer.service.helpers.RequestValidationHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    RequestValidationHelper requestValidationHelper;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    DTOToEntityTransform dtoToEntityTransform = new DTOToEntityTransform();

    CustomerDetails customerDetails;
    CustomerDetailsEntity customerDetailsEntity;

    @Before
    public void setUp() {
        customerService.requestValidationHelper = requestValidationHelper;
        customerService.customerRepository = customerRepository;

        dtoToEntityTransform.bCryptPasswordEncoder=bCryptPasswordEncoder;
        customerService.dtoToEntityTransform = dtoToEntityTransform;
        customerService.bCryptPasswordEncoder = bCryptPasswordEncoder;

        customerDetails = new CustomerDetails();
        customerDetails.setUserName("cust1");
        customerDetails.setPassword("cust1pass");

        customerDetailsEntity = new CustomerDetailsEntity();
        customerDetailsEntity.setUserName("cust1");
        customerDetailsEntity.setPassword("encrypted_cust1pass");
        customerDetailsEntity.setEnabled(1);
    }

    @Test
    public void login_success() throws AuthenticationException, GeneralException {
        Optional<CustomerDetailsEntity> optionalCustomerDetailsEntity
                = Optional.of(customerDetailsEntity);
        Mockito.when(customerRepository.findById("cust1"))
                .thenReturn(optionalCustomerDetailsEntity);

        Mockito.when(bCryptPasswordEncoder.matches("cust1pass", "encrypted_cust1pass"))
                .thenReturn(true);

        CustomerDetails custDetailsResponse = customerService.login(customerDetails);
        Assert.assertEquals("cust1", custDetailsResponse.getUserName());
        Assert.assertEquals("cust1pass", custDetailsResponse.getPassword());
        Assert.assertEquals(1, custDetailsResponse.getEnabled());
    }

    @Test(expected = AuthenticationException.class)
    public void login_customer_not_in_DB() throws AuthenticationException, GeneralException {
        Optional<CustomerDetailsEntity> optionalCustomerDetailsEntity
                = Optional.empty();
        Mockito.when(customerRepository.findById("cust1"))
                .thenReturn(optionalCustomerDetailsEntity);

        customerService.login(customerDetails);
    }

    @Test(expected = AuthenticationException.class)
    public void login_customer_not_enabled() throws AuthenticationException, GeneralException {
        Optional<CustomerDetailsEntity> optionalCustomerDetailsEntity
                = Optional.of(customerDetailsEntity);
        Mockito.when(customerRepository.findById("cust1"))
                .thenReturn(optionalCustomerDetailsEntity);

        customerService.login(customerDetails);
    }

    @Test(expected = AuthenticationException.class)
    public void login_password_does_not_match() throws AuthenticationException, GeneralException {
        Optional<CustomerDetailsEntity> optionalCustomerDetailsEntity
                = Optional.of(customerDetailsEntity);
        Mockito.when(customerRepository.findById("cust1"))
                .thenReturn(optionalCustomerDetailsEntity);

        Mockito.when(bCryptPasswordEncoder.matches("cust1pass", "encrypted_cust1pass"))
                .thenReturn(false);

        customerService.login(customerDetails);
    }

    @Test(expected = GeneralException.class)
    public void login_general_ex() throws AuthenticationException, GeneralException {
        Optional<CustomerDetailsEntity> optionalCustomerDetailsEntity
                = Optional.of(customerDetailsEntity);

        Mockito.doThrow(new RuntimeException("db error from Junit"))
                .when(customerRepository).findById(any());

        customerService.login(customerDetails);
    }

    @Test
    public void register_success() throws GeneralException, CustomerRegistrationException, ValidationException {
        customerDetails.setEmailId("emailId1@test");
        customerDetails.setMobileNumber("123456");

        Mockito.when(customerRepository.findByEmailId("emailId1@test"))
                .thenReturn(null);
        Mockito.when(customerRepository.findByMobileNumber("123456"))
                .thenReturn(null);

        Optional<CustomerDetailsEntity> optionalCustomerDetailsEntity
                = Optional.empty();
        Mockito.when(customerRepository.findById("cust1"))
                .thenReturn(optionalCustomerDetailsEntity);

        CustomerDetails custDetailsResponse = customerService.register(customerDetails);
        Assert.assertEquals(customerDetails, custDetailsResponse);
    }

    @Test(expected = CustomerRegistrationException.class)
    public void register_customer_exists_email() throws GeneralException, CustomerRegistrationException, ValidationException {
        customerDetails.setEmailId("emailId1@test");
        customerDetails.setMobileNumber("123456");

        List<CustomerDetailsEntity> customerDetailsEntities = new ArrayList<>();
        customerDetailsEntities.add(customerDetailsEntity);
        Mockito.when(customerRepository.findByEmailId("emailId1@test"))
                .thenReturn(customerDetailsEntities);

        customerService.register(customerDetails);
    }

    @Test(expected = CustomerRegistrationException.class)
    public void register_customer_exists_mobile() throws GeneralException, CustomerRegistrationException, ValidationException {
        customerDetails.setEmailId("emailId1@test");
        customerDetails.setMobileNumber("123456");

        List<CustomerDetailsEntity> customerDetailsEntities = new ArrayList<>();
        customerDetailsEntities.add(customerDetailsEntity);
        Mockito.when(customerRepository.findByMobileNumber("123456"))
                .thenReturn(customerDetailsEntities);

        customerService.register(customerDetails);
    }

    @Test(expected = CustomerRegistrationException.class)
    public void register_username_not_available() throws GeneralException, CustomerRegistrationException, ValidationException {
        customerDetails.setEmailId("emailId1@test");
        customerDetails.setMobileNumber("123456");

        Optional<CustomerDetailsEntity> optionalCustomerDetailsEntity
                = Optional.of(customerDetailsEntity);
        Mockito.when(customerRepository.findById("cust1"))
                .thenReturn(optionalCustomerDetailsEntity);

        customerService.register(customerDetails);
    }

    @Test(expected = ValidationException.class)
    public void register_validation_failed() throws GeneralException, CustomerRegistrationException, ValidationException {
        Mockito.doThrow(new ValidationException("validation ex from junit")).when(requestValidationHelper).validateRegisterRequest(any());

        customerService.register(customerDetails);
    }

    @Test(expected = GeneralException.class)
    public void register_db_save_failed() throws GeneralException, CustomerRegistrationException, ValidationException {
        customerDetails.setEmailId("emailId1@test");
        customerDetails.setMobileNumber("123456");

        List<CustomerDetailsEntity> customerDetailsEntities = new ArrayList<>();
        customerDetailsEntities.add(customerDetailsEntity);
        Mockito.when(customerRepository.findByEmailId("emailId1@test"))
                .thenReturn(null);

        Mockito.when(customerRepository.findByMobileNumber("123456"))
                .thenReturn(null);

        Optional<CustomerDetailsEntity> optionalCustomerDetailsEntity
                = Optional.empty();
        Mockito.when(customerRepository.findById("cust1"))
                .thenReturn(optionalCustomerDetailsEntity);

        Mockito.doThrow(new RuntimeException("save ex from junit")).when(customerRepository).save(any());

        customerService.register(customerDetails);
    }

}