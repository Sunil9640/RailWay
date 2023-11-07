package com.smart.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.daolayer.UserDao;
import com.smart.entities.User;



public class UserDetailsServiceImp  implements UserDetailsService {
	
	@Autowired
	private  UserDao  repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user1 = this.repository.getUserByUserName(username);
	          if (user1 != null) {
	        	  return new CustomUserDetails(user1);
	          }
	          throw new UsernameNotFoundException("user not available");
	}

}
