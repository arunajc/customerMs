package com.mybank.customer.repository;

import com.mybank.customer.entity.CustomerDetailsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerDetailsEntity, String> {

    List<CustomerDetailsEntity> findByEmailId(String emailId);
    List<CustomerDetailsEntity> findByMobileNumber(String mobileNumber);
}
