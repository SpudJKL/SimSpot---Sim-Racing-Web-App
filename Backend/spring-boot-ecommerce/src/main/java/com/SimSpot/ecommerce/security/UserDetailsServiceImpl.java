package com.SimSpot.ecommerce.security;

import com.SimSpot.ecommerce.dao.UserRepository;
import com.SimSpot.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class UserDetailsServiceImpl implements UserDetailsService
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * This is a new Instantiation of UserRepository object
     */
    @Autowired
    UserRepository userRepository;

    /**
     * This method loadUserByUsername takes a String username as a parameter
     * We use userRepositorys method findByUserame and pass in the given username
     * and if found we return the UserDetailsImpl.build() method
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

}
