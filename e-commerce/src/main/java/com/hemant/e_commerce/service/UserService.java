package com.hemant.e_commerce.service;


import com.hemant.e_commerce.exception.UserException;
import com.hemant.e_commerce.model.User;
import org.springframework.stereotype.Service;


public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
