package com.hemant.e_commerce.controller;

import com.hemant.e_commerce.config.JwtProvider;
import com.hemant.e_commerce.exception.UserException;
import com.hemant.e_commerce.model.User;
import com.hemant.e_commerce.repository.UserRepository;
import com.hemant.e_commerce.request.LoginRequest;
import com.hemant.e_commerce.response.AuthResponse;
import com.hemant.e_commerce.service.CustomUserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private LoginRequest loginRequest;

    @Autowired
    private CustomUserServiceImplementation customUserServiceImplementation;

    public AuthController(LoginRequest loginRequest ){
        this.loginRequest=loginRequest;

    }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
        String email= user.getEmail();
        String password= user.getPassword();
        String  firstName= user.getFirstName();
        String lastName= user.getLastName();

        User isEmailExist=userRepository.findByEmail(email);

        if(isEmailExist!=null){
            throw new UserException("Email is Already Used with Another Account");
        }


        User createdUser=new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        User savedUser=userRepository.save(createdUser);

        Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup Success");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

        @PostMapping("/signin")
        public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest){

        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();

        Authentication authentication=authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
            String token=jwtProvider.generateToken(authentication);
            AuthResponse authResponse=new AuthResponse();
            authResponse.setJwt(token);
            authResponse.setMessage("Signin Success");
            return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
        }

        private Authentication authenticate(String username, String password){
            UserDetails userDetails=customUserServiceImplementation.loadUserByUsername(username);

            if(userDetails==null){
                throw new BadCredentialsException("Invalid Username");
            }

            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("Invalid Password.....");
            }

            return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        }
}
