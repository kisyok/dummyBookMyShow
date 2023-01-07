package com.dummy.bookmyshow.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dummy.bookmyshow.entity.User;
import com.dummy.bookmyshow.enums.UserType;
import com.dummy.bookmyshow.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findUserByUserName(username);

		ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(); 
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserType().toString()));

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getAuthentication(),
			grantedAuthorities);
	}

}
