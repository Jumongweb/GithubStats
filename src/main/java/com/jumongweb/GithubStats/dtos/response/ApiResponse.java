package com.jumongweb.GithubStats.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    Object UserResponse;

}
