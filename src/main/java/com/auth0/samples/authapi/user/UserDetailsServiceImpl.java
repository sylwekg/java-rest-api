package com.auth0.samples.authapi.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private ApplicationUserRepository applicationUserRepository;

	public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
//		if (applicationUser == null) {
//			throw new UsernameNotFoundException(username);
//		}
//		return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
//	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		ApplicationUser applicationUser = applicationUserRepository.findByEmail(email);
		if (applicationUser == null) {
			throw new UsernameNotFoundException(email);
		}
		return new User(applicationUser.getEmail(), applicationUser.getPassword(), emptyList());
	}

}
