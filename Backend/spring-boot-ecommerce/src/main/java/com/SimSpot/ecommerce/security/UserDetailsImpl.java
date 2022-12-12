package com.SimSpot.ecommerce.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.SimSpot.ecommerce.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class UserDetailsImpl implements UserDetails a spring boot standard interface for user details methods
 * such as Username and Password.
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    /**
     * Class Variables
     */

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;


    /**
     * A collect object extending the GrantedAuthority, an interface from Spring security.
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * This is the Constructor for UserDetailsImpl
     * @param id
     * @param email
     * @param password
     * @param authorities
     */
    public UserDetailsImpl(Long id, String email,
                           String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * This method Build, takes a User object, gets the List of roles for the provided User object, maps a new
     *
     * @param user
     * @return
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    /**
     * Below are some overridden methods from the interface GrantedAuthority
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}