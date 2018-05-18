package com.luxmart.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.luxmart.model.User;
import com.luxmart.model.security.CurrentUser;
import com.luxmart.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService {


	/** The application logger */
	private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);

	@Autowired
	private UserRepository userRepository;


	 @Override
	    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
 
		 User user = userRepository.findByUsername(username);
			if (null == user) {
				LOG.warn("Username {} not found", username);
				throw new UsernameNotFoundException("Username " + username + " not found");
			}
	        
	        return new CurrentUser(user);
	    }



}
