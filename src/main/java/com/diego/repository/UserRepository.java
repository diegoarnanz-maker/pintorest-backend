package com.diego.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diego.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);


}
