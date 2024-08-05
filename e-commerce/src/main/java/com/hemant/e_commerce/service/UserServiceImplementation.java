package com.hemant.e_commerce.service;

import com.hemant.e_commerce.config.JwtProvider;
import com.hemant.e_commerce.exception.UserException;
import com.hemant.e_commerce.model.User;
import com.hemant.e_commerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{

    private UserRepository userRepository;

    private JwtProvider jwtProvider;


    public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider){
        this.userRepository=userRepository;
        this.jwtProvider=jwtProvider;
    }
    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User>user=userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserException("user not found with id:"+ userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email=jwtProvider.getEmailFromToken(jwt);
        User user=userRepository.findByEmail(email);

        if(user==null){
            throw new UserException("user not found with  email"+email);
        }

        return user;
    }
}
