package com.mybank.customer.repository;

import org.springframework.data.repository.CrudRepository;

import com.mybank.customer.entity.UserDetailsEntity;

public interface UserRepository extends CrudRepository<UserDetailsEntity, String> {
}