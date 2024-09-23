package com.diego.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diego.config.JwtProvider;
import com.diego.model.User;
import com.diego.repository.UserRepository;
import com.diego.request.LoginRequest;
import com.diego.response.AuthRespose;
import com.diego.service.CustomeUserDetailsService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private CustomeUserDetailsService customeUserDetailsService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired    
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public AuthRespose createUser(@RequestBody User user) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();

        User isExistEmail = userRepository.findByEmail(email);
        if (isExistEmail != null) {
            throw new Exception("Email already exist");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFullName(fullName);

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        AuthRespose res = new AuthRespose();

        res.setJwt(token);
        res.setMessage("User created successfully");

        return res;
    }

    @PostMapping("/signin")
    public AuthRespose siginHandler(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        AuthRespose res = new AuthRespose();
        res.setJwt(token);
        res.setMessage("Login successfully");

        return res;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customeUserDetailsService.loadUserByUsername(username);

        if(userDetails == null) {
            throw new BadCredentialsException("Username or password is incorrect");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Username or password is incorrect");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    } 

}
