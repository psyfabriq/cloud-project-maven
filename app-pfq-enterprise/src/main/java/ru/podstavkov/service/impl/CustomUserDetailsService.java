package ru.podstavkov.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.podstavkov.entity.User;
import ru.podstavkov.model.UserPrincipal;
import ru.podstavkov.repository.UserRepository;
import ru.podstavkov.utils.annotation.LogBefore;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	 @Autowired
	    UserRepository userRepository;

	    @Override
	    @Transactional
	    @LogBefore
	    public UserDetails loadUserByUsername(String username)
	            throws UsernameNotFoundException {
	        // Let people login with either username or email
	        User user = userRepository.findByUsernameOrEmail(username, username)
	                .orElseThrow(() -> 
	                        new UsernameNotFoundException("User not found with username or email : " + username)
	        );

	        return UserPrincipal.create(user);
	    }

	    // This method is used by JWTAuthenticationFilter
	    @Transactional
	    public UserDetails loadUserById(Long id) {
	        User user = userRepository.findById(id).orElseThrow(
	            () -> new UsernameNotFoundException("User not found with id : " + id)
	        );

	        return UserPrincipal.create(user);
	    }
	   
}