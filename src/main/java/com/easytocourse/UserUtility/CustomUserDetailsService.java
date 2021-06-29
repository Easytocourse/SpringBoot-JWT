package com.easytocourse.UserUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.easytocourse.model.User;
import com.easytocourse.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userrepo.findByEmail(username);
		if (user == null)
			throw new UsernameNotFoundException("User not Found");

		return new CustomUserDetails(user);
	}

}
