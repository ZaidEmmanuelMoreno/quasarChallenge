package com.quasar.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.quasar.entity.Role;
import com.quasar.entity.User;
import com.quasar.enums.RolesEnum;
import com.quasar.repository.RoleRepository;
import com.quasar.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUsername(username);

		if (!userOptional.isPresent()) {
			throw new UsernameNotFoundException("Could not find user");
		}
		User user = userOptional.get();
		Optional<Role> roleOptional = roleRepository.findByIdUserId(user.getId());
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(RolesEnum.PREFIX.getValue().concat(roleOptional.get().getName()));
		
		user.setGrantedAuthorities(grantedAuthorities);
		return user;
		
	}

}