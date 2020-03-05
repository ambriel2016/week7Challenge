package com.cristian.demo;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetails extends org.springframework.security.core.userdetails.User {
    private User user;

    public UserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }

    public UserDetails(User user, String password,
                             Collection<?extends GrantedAuthority> authorities) {
        super(user.getUsername(), password, authorities);
        this.user = user;
    }

    public UserDetails(User user, boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked,
                             Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}