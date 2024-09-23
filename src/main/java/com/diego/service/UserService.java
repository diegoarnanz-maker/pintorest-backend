package com.diego.service;

import com.diego.model.User;

public interface UserService {

    public User findUserById(Long userId) throws Exception;

    public User findUserByJwt (String jwt) throws Exception;
}
