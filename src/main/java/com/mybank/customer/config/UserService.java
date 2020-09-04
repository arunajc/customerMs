package com.mybank.customer.config;

import com.mybank.customer.entity.UserDetailsEntity;
import com.mybank.customer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("ArTest - in userservice");
		Optional<UserDetailsEntity> userDetailsEntityOp = userRepository.findById(username);
		if (!userDetailsEntityOp.isPresent()) {
			return null;
		}
		UserDetailsEntity userDetailsEntity = userDetailsEntityOp.get();
		return new User(username, userDetailsEntity.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(userDetailsEntity.getRole()));
	}
}
