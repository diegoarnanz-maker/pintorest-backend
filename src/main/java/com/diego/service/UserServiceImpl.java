package com.diego.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diego.config.JwtProvider;
import com.diego.model.User;
import com.diego.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired  
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> opt = userRepository.findById(userId);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new Exception("User not found: " + userId);
    }

    @Override
    public User findUserByJwt(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromToken(jwt);
        if (email == null) {
            throw new Exception("Invalid token");
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found: " + email);
        }

        return user;
    }

}
