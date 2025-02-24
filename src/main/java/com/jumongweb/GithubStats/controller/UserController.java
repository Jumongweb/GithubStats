package com.jumongweb.GithubStats.controller;

import com.jumongweb.GithubStats.data.model.User;
import com.jumongweb.GithubStats.dtos.request.LoginRequest;
import com.jumongweb.GithubStats.dtos.request.RegisterRequest;
import com.jumongweb.GithubStats.dtos.response.ApiResponse;
import com.jumongweb.GithubStats.dtos.response.LoginResponse;
import com.jumongweb.GithubStats.dtos.response.RegisterResponse;
import com.jumongweb.GithubStats.exception.EmailAlreadyExistException;
import com.jumongweb.GithubStats.exception.InvalidUsernameOrPasswordException;
import com.jumongweb.GithubStats.exception.ShortPasswordException;
import com.jumongweb.GithubStats.exception.UserDoesNotExistException;
import com.jumongweb.GithubStats.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            RegisterResponse registerResponse = userService.register(registerRequest);
            return new ResponseEntity<>(new ApiResponse(true, registerResponse), HttpStatus.CREATED);
        } catch (EmailAlreadyExistException | IllegalArgumentException | ShortPasswordException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try{
            LoginResponse loginResponse = userService.login(loginRequest);
            return new ResponseEntity<>(new ApiResponse(true, loginResponse), HttpStatus.OK);
        } catch (UserDoesNotExistException | InvalidUsernameOrPasswordException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
