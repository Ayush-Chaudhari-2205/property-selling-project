package com.propertyguru.security;

import com.propertyguru.dao.UserDao;
import com.propertyguru.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
	// dep : dao layer
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email not found!!!!"));
		return new CustomUserDetails(user);
	}

}
