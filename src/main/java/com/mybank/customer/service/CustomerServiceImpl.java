package com.mybank.customer.service;

import com.mybank.customer.entity.CustomerDetailsEntity;
import com.mybank.customer.exception.AuthenticationException;
import com.mybank.customer.exception.CustomerRegistrationException;
import com.mybank.customer.exception.GeneralException;
import com.mybank.customer.exception.GeneralException.GeneralError;
import com.mybank.customer.exception.ValidationException;
import com.mybank.customer.model.CustomerDetails;
import com.mybank.customer.repository.CustomerRepository;
import com.mybank.customer.service.helpers.DTOToEntityTransform;
import com.mybank.customer.service.helpers.RequestValidationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	RequestValidationHelper requestValidationHelper;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	DTOToEntityTransform dtoToEntityTransform;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public CustomerDetails login(CustomerDetails customerDetails)
			throws AuthenticationException, GeneralException {
		LOGGER.info("Authenticating user: {}",
				null!=customerDetails? customerDetails.getUserName(): null);
		try {
			Optional<CustomerDetailsEntity> customerDetailsEntity =
					customerRepository.findById(customerDetails.getUserName());

			if(!customerDetailsEntity.isPresent()){
				//user not found in DB - throw AuthenticationError -
				// CustomerErrorHandler will handle sending response back
				LOGGER.warn("User not found: {}", customerDetails.getUserName());
				throw new AuthenticationException(
						AuthenticationException.AuthenticationError.AUTHENTICATION_FAILED);
			}

			if(customerDetailsEntity.get().getEnabled() != 1){
				//user invalid
				LOGGER.warn("User not active: {}", customerDetails.getUserName());
				throw new AuthenticationException(
						AuthenticationException.AuthenticationError.INACTIVE_CUSTOMER);
			}
			customerDetails.setEnabled(1);

			if(!bCryptPasswordEncoder.matches(
					customerDetails.getPassword(), customerDetailsEntity.get().getPassword())){
				//wrong password - throw AuthenticationError
				LOGGER.warn("Wrong password for user: {}", customerDetails.getUserName());
				throw new AuthenticationException(
						AuthenticationException.AuthenticationError.AUTHENTICATION_FAILED);
			}
			LOGGER.info("User authenticated successfully: {}", customerDetails.getUserName());
		} catch(Exception ex) {
			if(ex instanceof AuthenticationException) throw ex;
			throw new GeneralException(GeneralError.UNEXPECTED_ERROR);
		}
		return customerDetails;
	}

	@Override
	public CustomerDetails register(CustomerDetails customerDetails)
			throws CustomerRegistrationException, GeneralException, ValidationException {

		try {
			LOGGER.info("Creating user: userName:{}, firstName:{}, lastName:{}",
					customerDetails.getUserName(), customerDetails.getFirstName(), customerDetails.getLastName());

			requestValidationHelper.validateRegisterRequest(customerDetails);
			//checking of customer already exists(using email and mobile)
			List<CustomerDetailsEntity> existingUsers =
					customerRepository.findByEmailId(customerDetails.getEmailId());
			if(null!=existingUsers && !existingUsers.isEmpty()){
				LOGGER.warn("Customer registration Failed - Customer with same emailId exists. user: {}",
						customerDetails.getUserName());
				throw new CustomerRegistrationException(
						CustomerRegistrationException.CustomerRegistrationError.CUSTOMER_ALREADY_EXISTS_EMAIL);
			}

			existingUsers =
					customerRepository.findByMobileNumber(customerDetails.getMobileNumber());
			if(null!=existingUsers && !existingUsers.isEmpty()){
				LOGGER.warn("Customer registration Failed - Customer with same mobileNumber exists. user: {}",
						customerDetails.getUserName());
				throw new CustomerRegistrationException(
						CustomerRegistrationException.CustomerRegistrationError.CUSTOMER_ALREADY_EXISTS_MOBILE);
			}

			//checking if userName is already taken
			Optional<CustomerDetailsEntity> existingUser =
					customerRepository.findById(customerDetails.getUserName());
			if(existingUser.isPresent()){
				LOGGER.warn("Customer registration Failed - Username already taken. user: {}",
						customerDetails.getUserName());
				throw new CustomerRegistrationException(
						CustomerRegistrationException.CustomerRegistrationError.USERNAME_ALREADY_TAKEN);
			}

			CustomerDetailsEntity customerDetailsEntity = dtoToEntityTransform.convertUserDetails(customerDetails);

			LOGGER.info("Saving user in DB. user: {}", customerDetailsEntity.getUserName());
			customerRepository.save(customerDetailsEntity);
			LOGGER.info("Saving user in DB completed. user: {}", customerDetailsEntity.getUserName());

		} catch(Exception ex) {
			if(ex instanceof CustomerRegistrationException
			|| ex instanceof ValidationException) throw ex;
			throw new GeneralException(GeneralError.UNEXPECTED_ERROR);
		}

		return customerDetails;
	}
}
