package com.mybank.customer.service.helpers;

import com.mybank.customer.entity.CustomerDetailsEntity;
import com.mybank.customer.model.CustomerDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DTOToEntityTransform {
    private static final Logger LOGGER = LoggerFactory.getLogger(DTOToEntityTransform.class);

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${customer.creation.user:CustomerMs}")
    protected String creationUser;

    public CustomerDetailsEntity convertUserDetails(CustomerDetails customerDetails){
        LOGGER.info("Converting userDetails to entity. user: {}",
                null!=customerDetails? customerDetails.getUserName(): null);
        CustomerDetailsEntity customerDetailsEntity = null;
        if(null!= customerDetails) {
            customerDetailsEntity = new CustomerDetailsEntity();

            customerDetailsEntity.setUserName(customerDetails.getUserName());
            customerDetailsEntity.setPassword(bCryptPasswordEncoder.encode(customerDetails.getPassword()));
            customerDetailsEntity.setFirstName(customerDetails.getFirstName());
            customerDetailsEntity.setLastName(customerDetails.getLastName());
            customerDetailsEntity.setEmailId(customerDetails.getEmailId());
            customerDetailsEntity.setMobileNumber(customerDetails.getMobileNumber());
            customerDetailsEntity.setEnabled(1);
            customerDetailsEntity.setInsertedBy(creationUser);
            customerDetailsEntity.setUpdatedBy(creationUser);
        }

        LOGGER.info("Converting userDetails to entity completed. user: {}",
                null!=customerDetails? customerDetails.getUserName(): null);
        return customerDetailsEntity;
    }
}
