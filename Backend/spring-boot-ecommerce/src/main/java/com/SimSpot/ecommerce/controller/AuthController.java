package com.SimSpot.ecommerce.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.SimSpot.ecommerce.dao.CustomerRepository;
import com.SimSpot.ecommerce.dao.RoleRepository;
import com.SimSpot.ecommerce.dao.UserRepository;
import com.SimSpot.ecommerce.entity.Customer;
import com.SimSpot.ecommerce.entity.ERole;
import com.SimSpot.ecommerce.entity.Role;
import com.SimSpot.ecommerce.entity.User;
import com.SimSpot.ecommerce.payload.request.LoginRequest;
import com.SimSpot.ecommerce.payload.request.SignupRequest;
import com.SimSpot.ecommerce.payload.response.JwtResponse;
import com.SimSpot.ecommerce.payload.response.MessageResponse;
import com.SimSpot.ecommerce.security.JwtUtils;
import com.SimSpot.ecommerce.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This AuthController class sets out the REST endpoints
 * for Post mapping for the SignUp and LoginIn request
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // Instance variables
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    /** This method authenticateUser is executed when a POST request from LoginRequest is made
     * It first sets up the users password and username and generates a user authenticate token
     * It then takes advantage of UserDetails to be able to select a role mapped to the user. It then returns a JwtResponse with
     * the user's username, email and ID
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    /**
     * This method registerUser is executed when a  POST request from signUpRequest is made
     * It first checks if the user's email already exists by extending the userRepository methods
     * If the user's email is not found, It creates a new User object with the provided username, email and then encodes the password in the DB
     * By default it adds a user to the user role, saves the user object and returns a Message response if successful
     * @param signUpRequest
     * @return
     */

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new customers's account
        Customer customer = new Customer(signUpRequest.getFirstName(),
                                        signUpRequest.getLastName(),
                                        signUpRequest.getEmail());

        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        // Create new user's account
        userRepository.save(user);
        // Create new Customer account
        customerRepository.save(customer);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}