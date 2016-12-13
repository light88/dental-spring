package com.dntistpro.oauth.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by vrudyk on 12/3/2016.
 */
public class UserAuthentication implements Authentication {

  private final User user;
  private boolean authenticated = true;

  public UserAuthentication(User user) {
    this.user = user;
  }

  @Override
  public String getName() {
    return user.getUsername();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getAuthorities();
  }

  @Override
  public Object getCredentials() {
    return user.getPassword();
  }

  @Override
  public User getDetails() {
    return user;
  }


  @Override
  public Object getPrincipal() {
    return user.getUsername();
  }

  @Override
  public boolean isAuthenticated() {
    return authenticated;
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }
}