//package com.jumongweb.GithubStats;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jumongweb.GithubStats.dtos.request.RegisterRequest;
//import com.jumongweb.GithubStats.dtos.response.RegisterResponse;
//import com.jumongweb.GithubStats.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest
//@AutoConfigureMockMvc
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setUp() {
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    public void testRegister_Success() throws Exception {
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setEmail("test@example.com");
//        registerRequest.setPassword("password123");
//        registerRequest.setFirstName("John");
//        registerRequest.setLastName("Doe");
//
//        RegisterResponse registerResponse = new RegisterResponse();
//        registerResponse.setMessage("Registration Successful");
//
//        when(userService.register(any(RegisterRequest.class))).thenReturn(registerResponse);
//
//        // Act & Assert
////        mockMvc.perform(post("/api/v1/register")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(registerRequest)))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.success").value(true))
////                .andExpect(jsonPath("$.message").value("User registered successfully"))
////                .andExpect(jsonPath("$.data.message").value("Registration Successful"));
//
////        this.mockMvc.perform(post("/api/v1/register")
//
//
//        verify(userService, times(1)).register(any(RegisterRequest.class));
//    }
//
//    //}
//
//
//
//}
