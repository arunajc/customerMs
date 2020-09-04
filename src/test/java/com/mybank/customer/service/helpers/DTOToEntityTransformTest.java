package com.mybank.customer.service.helpers;

import com.mybank.customer.entity.CustomerDetailsEntity;
import com.mybank.customer.model.CustomerDetails;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class DTOToEntityTransformTest {

    @InjectMocks
    DTOToEntityTransform dtoToEntityTransform;

    CustomerDetails customerDetails;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setUp() {

        customerDetails = new CustomerDetails();
        customerDetails.setUserName("cust1");
        customerDetails.setPassword("cust1pass");

        dtoToEntityTransform.creationUser = "creation_user";
        dtoToEntityTransform.bCryptPasswordEncoder = bCryptPasswordEncoder;
        Mockito.when(bCryptPasswordEncoder.encode(customerDetails.getPassword()))
                .thenReturn("encrypted_password");
    }

    @Test
    public void convertUserDetails_fulldata_success() {
        customerDetails.setFirstName("fname");
        customerDetails.setLastName("lname");
        customerDetails.setEmailId("emailId");
        customerDetails.setMobileNumber("112233");

        CustomerDetailsEntity customerDetailsEntity =
                dtoToEntityTransform.convertUserDetails(customerDetails);

        Assert.assertEquals("cust1", customerDetailsEntity.getUserName());
        Assert.assertEquals("encrypted_password", customerDetailsEntity.getPassword());
        Assert.assertEquals("fname", customerDetailsEntity.getFirstName());
        Assert.assertEquals("lname", customerDetailsEntity.getLastName());
        Assert.assertEquals("emailId", customerDetailsEntity.getEmailId());
        Assert.assertEquals("112233", customerDetailsEntity.getMobileNumber());
        Assert.assertEquals(1, customerDetailsEntity.getEnabled());
        Assert.assertEquals("creation_user", customerDetailsEntity.getInsertedBy());
        Assert.assertEquals("creation_user", customerDetailsEntity.getUpdatedBy());
    }

    @Test
    public void convertUserDetails_partialdata_success() {

        CustomerDetailsEntity customerDetailsEntity =
                dtoToEntityTransform.convertUserDetails(customerDetails);

        Assert.assertEquals("cust1", customerDetailsEntity.getUserName());
        Assert.assertEquals("encrypted_password", customerDetailsEntity.getPassword());
        Assert.assertNull(customerDetailsEntity.getFirstName());
        Assert.assertNull(customerDetailsEntity.getLastName());
        Assert.assertNull(customerDetailsEntity.getEmailId());
        Assert.assertNull(customerDetailsEntity.getMobileNumber());
        Assert.assertEquals(1, customerDetailsEntity.getEnabled());
        Assert.assertEquals("creation_user", customerDetailsEntity.getInsertedBy());
        Assert.assertEquals("creation_user", customerDetailsEntity.getUpdatedBy());
    }

    @Test
    public void convertUserDetails_null_input() {

        CustomerDetailsEntity customerDetailsEntity =
                dtoToEntityTransform.convertUserDetails(null);

        Assert.assertNull(customerDetailsEntity);
    }
}