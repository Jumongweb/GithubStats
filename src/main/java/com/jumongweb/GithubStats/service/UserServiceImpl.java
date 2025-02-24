package com.jumongweb.GithubStats.service;

import com.jumongweb.GithubStats.data.model.User;
import com.jumongweb.GithubStats.data.repositories.UserRepository;
import com.jumongweb.GithubStats.dtos.request.LoginRequest;
import com.jumongweb.GithubStats.dtos.request.RegisterRequest;
import com.jumongweb.GithubStats.dtos.response.LoginResponse;
import com.jumongweb.GithubStats.dtos.response.RegisterResponse;
import com.jumongweb.GithubStats.exception.EmailAlreadyExistException;
import com.jumongweb.GithubStats.exception.InvalidUsernameOrPasswordException;
import com.jumongweb.GithubStats.exception.ShortPasswordException;
import com.jumongweb.GithubStats.exception.UserDoesNotExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        validate(registerRequest);
        String email = registerRequest.getEmail().toLowerCase().replaceAll("\\s", "");
//        String password = passwordEncoder.encode(registerRequest.getPassword());
        String password = registerRequest.getPassword();
        String firstName = registerRequest.getFirstName().toLowerCase().replaceAll("\\s", "");
        String lastName = registerRequest.getLastName().toLowerCase().replaceAll("\\s", "");

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        userRepository.save(newUser);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setMessage("Registration Successful");
        return registerResponse;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        validate(loginRequest);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("Login successful");
        return loginResponse;
    }

    private void validate(LoginRequest loginRequest) {
        if(loginRequest == null){
            throw new IllegalArgumentException("LoginRequest cannot be null");
        }
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        email = loginRequest.getEmail().toLowerCase().replaceAll("\\s", "");
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserDoesNotExistException("User does not exist"));
        log.info("Username ------------>", user.getEmail());
        log.info("Password ------------>",user.getPassword());
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new InvalidUsernameOrPasswordException("Invalid username or password");
//        }

        if (!password.equals(user.getPassword())) {
            throw new InvalidUsernameOrPasswordException("Invalid username or password");
        }

    }


    private void validate(RegisterRequest registerRequest) {
        if (registerRequest == null) {
            throw new IllegalArgumentException("RegisterRequest cannot be null");
        }

        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String firstName = registerRequest.getFirstName();
        String lastName = registerRequest.getLastName();

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("FirstName cannot be null or empty");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("LastName cannot be null or empty");
        }

        if (userRepository.existsByEmail(email.toLowerCase())
                || userRepository.existsByEmailIgnoreCase(email.toLowerCase())) {
            throw new EmailAlreadyExistException("User with same email already exist");
        }

        if (password.length() < 8) {
            throw new ShortPasswordException("Password must be at least 8 characters long");
        }

        if (!firstName.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("First name contains invalid characters");
        }

        if (!lastName.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("Last name contains invalid characters");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

    }
}
