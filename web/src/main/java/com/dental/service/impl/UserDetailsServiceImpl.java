package com.dental.service.impl;

import com.dental.persistence.entity.User;
import com.dental.persistence.repository.UserRepository;
import com.dental.provider.DentalUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 06.04.2015.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private static SimpleGrantedAuthority roleAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
  private static SimpleGrantedAuthority roleUser = new SimpleGrantedAuthority("ROLE_USER");

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
    final User user = userRepository.findByEmail(email);
    if (user == null)
      throw new UsernameNotFoundException("user name not found" + email);

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(roleUser);
    return new DentalUserDetails(email, user.getPassword(), authorities, user);
  }
}