package com.jumongweb.GithubStats.service;

import com.jumongweb.GithubStats.dtos.request.LoginRequest;
import com.jumongweb.GithubStats.dtos.request.RegisterRequest;
import com.jumongweb.GithubStats.dtos.response.LoginResponse;
import com.jumongweb.GithubStats.dtos.response.RegisterResponse;

public interface UserService {
    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
