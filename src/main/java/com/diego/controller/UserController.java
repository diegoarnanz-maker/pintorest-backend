package com.diego.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.diego.model.User;
// import com.diego.repository.UserRepository;
import com.diego.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;

// import java.util.List;


@RestController
public class UserController {

    
    @Autowired
    private UserService userService;

    @GetMapping ("/api/users/profile")
    public User findUserByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        return user;


    }

    // @Autowired
    // private UserRepository userRepository;

    // @GetMapping("/users")
    // public List<User> getAllUsers() {

    //     List<User> users = userRepository.findAll();
    //     return users;
    // }
    

    // @PostMapping("/users")
    // public User createUser(@RequestBody User user) throws Exception {
        
    //     User isExist = userRepository.findByEmail(user.getEmail());
    //     if(isExist!=null) {
    //         throw new Exception("usuario ya existe con email"+user.getEmail());
    //     }

    //     User savedUser = userRepository.save(user);
    //     return savedUser;
    // }

    // @DeleteMapping("/users/{userId}")
    // public String deleteUser(@PathVariable Long userId) throws Exception {

    //     userRepository.deleteById(userId);
    //     return "Usuario eliminado con id: "+userId;
    // }

    // public User findByEmail(String email) throws Exception {
    //     User user = userRepository.findByEmail(email);
    //     if(user==null) {
    //         throw new Exception("usuario no encontrado con email"+email);
    //     }
    //     return user;
    // }



}
