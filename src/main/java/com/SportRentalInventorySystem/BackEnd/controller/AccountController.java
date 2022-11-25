package com.SportRentalInventorySystem.BackEnd.controller;

/**
* *
*
* @author  Meron seyoum
* @version 1.0
* @since   2022-09-24
*/

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SportRentalInventorySystem.BackEnd.Payload.Request.ForgetPasswordRequest;
import com.SportRentalInventorySystem.BackEnd.Payload.Request.LoginRequest;
import com.SportRentalInventorySystem.BackEnd.Payload.Request.SignupRequest;
import com.SportRentalInventorySystem.BackEnd.Payload.Response.JwtResponse;
import com.SportRentalInventorySystem.BackEnd.Payload.Response.MessageResponse;
import com.SportRentalInventorySystem.BackEnd.Security.services.UserDetailsImpl;
import com.SportRentalInventorySystem.BackEnd.model.ERole;
import com.SportRentalInventorySystem.BackEnd.model.Role;
import com.SportRentalInventorySystem.BackEnd.model.User;
import com.SportRentalInventorySystem.BackEnd.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

import com.SportRentalInventorySystem.BackEnd.repository.RoleRepository;
import com.SportRentalInventorySystem.BackEnd.Security.jwt.JwtUtils;

//if user does't have account and has a unique Username and email the method will register the user 
// depending on the role the default ROLE is USER but if the Account is created by Manager it may have an ROLE_ADMIN OR ROLE_MODERATOR roles

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

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

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmail(),
                signUpRequest.getPhoneNumber(), signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getStatus());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: user Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Admin Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: moderator Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error:user default Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest) {
        User emailfound = null;
//        check if email exist in database
        
            if (!userRepository.existsByEmail(forgetPasswordRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email Doesn't exist!"));
            }
            
                // if email exist generate token and retrieve the email i n db
                 emailfound = userRepository.findByEmail(forgetPasswordRequest.getEmail());

                String token = RandomString.make(30);
            
           
        return ResponseEntity.ok(new MessageResponse(emailfound.getEmail()));
    }

}