package com.pfm.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pfm.model.User;
import com.pfm.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
    public CustomUserDetailsService(UserRepository userRepository) { this.userRepository = userRepository; }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByEmail(username)
		.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

		return new CustomUserDetails(u);
	}
	

}
