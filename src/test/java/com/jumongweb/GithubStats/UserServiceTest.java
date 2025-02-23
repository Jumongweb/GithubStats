package com.jumongweb.GithubStats;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
import com.jumongweb.GithubStats.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @Mock
    private PasswordEncoder passwordEncoder;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");

//        loginRequest = new LoginRequest();
//        loginRequest.setEmail("test@example.com");
//        loginRequest.setPassword("password123");
    }

    @Test
    public void testRegister_Success() throws EmailAlreadyExistException, ShortPasswordException {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        RegisterResponse response = userService.register(registerRequest);
        assertNotNull(response);
        assertEquals("Registration Successful", response.getMessage());
        verify(userRepository, times(1)).existsByEmail("test@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegister_NullInput() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.register(null));
        assertEquals("RegisterRequest cannot be null", e.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegister_EmptyEmailFields() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("firstName");
        registerRequest.setLastName("lastName");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
        assertEquals("Email cannot be null or empty", e.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegister_NullEmailFields() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(null);
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("firstName");
        registerRequest.setLastName("lastName");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
        assertEquals("Email cannot be null or empty", e.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegister_EmptyPasswordFields() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("email@email.com");
        registerRequest.setPassword("");
        registerRequest.setFirstName("firstName");
        registerRequest.setLastName("lastName");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
        assertEquals("Password cannot be null or empty", e.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegister_NullPasswordFields() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("email@email.com");
        registerRequest.setPassword(null);
        registerRequest.setFirstName("firstName");
        registerRequest.setLastName("lastName");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
        assertEquals("Password cannot be null or empty", e.getMessage());
        verify(userRepository, never()).save(any(User.class));

    }

    @Test
    public void testRegister_NullFirstNameFields() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("email@email.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstName(null);
        registerRequest.setLastName("lastName");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
        assertEquals("FirstName cannot be null or empty", e.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegister_EmptyFirstNameFields() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("email@email.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstName("");
        registerRequest.setLastName("lastName");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
        assertEquals("FirstName cannot be null or empty", e.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegister_NullLastNameFields() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("email@email.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstName("firstName");
        registerRequest.setLastName(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
        assertEquals("LastName cannot be null or empty", e.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegister_EmptyLastNameFields() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("email@email.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstName("firstName");
        registerRequest.setLastName("");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
        assertEquals("LastName cannot be null or empty", e.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegister_EmailAlreadyExist(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("email@email.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstName("firstName");
        registerRequest.setLastName("lastName");
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);
        EmailAlreadyExistException exception = assertThrows(
                EmailAlreadyExistException.class,
                () -> userService.register(registerRequest)
        );
        assertEquals("User with same email already exist", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(registerRequest.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }
//
//    @Test
//    public void testRegister_DatabaseException() {
//        RegisterRequest registerRequest = new RegisterRequest();
//        registerRequest.setEmail("test@example.com");
//        registerRequest.setPassword("password123");
//
//        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));
//        assertThrows(RuntimeException.class, () -> userService.register(registerRequest));
//    }

    @Test
    public void testRegister_ShortPassword(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("email@email.com");
        registerRequest.setPassword("123");
        registerRequest.setFirstName("firstName");
        registerRequest.setLastName("lastName");

        ShortPasswordException exception = assertThrows(ShortPasswordException.class, ()-> userService.register(registerRequest));
        assertEquals("Password must be at least 8 characters long", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegister_EmailAlreadyExist_CaseInsensitive() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("Test@Example.com");
        registerRequest.setPassword("password");
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");

        when(userRepository.existsByEmailIgnoreCase("test@example.com")).thenReturn(true);

        EmailAlreadyExistException exception = assertThrows(
                EmailAlreadyExistException.class,
                () -> userService.register(registerRequest)
        );
        assertEquals("User with same email already exist", exception.getMessage());
        verify(userRepository, times(1)).existsByEmailIgnoreCase("test@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegister_FirstNameContainsSpecialCharacters() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("J@hn!"); // Invalid characters
        registerRequest.setLastName("Doe");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
        assertEquals("First name contains invalid characters", e.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegister_lastNameContainsSpecialCharacters() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("John"); // Invalid characters
        registerRequest.setLastName("Do!e");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.register(registerRequest));
        assertEquals("Last name contains invalid characters", e.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegister_InvalidEmail() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("invalid-email");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.register(registerRequest));

        assertEquals("Invalid email format", exception.getMessage());
        verify(userRepository, never()).save(any());
    }


    @Test
    public void testLogin_Success() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        userService.register(registerRequest);
        User registeredUser = new User();
        registeredUser.setEmail("test@example.com");
        registeredUser.setPassword("password123"); // Store password in plain text
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(registeredUser));
        LoginResponse loginResponse = userService.login(loginRequest);

        assertThat(loginResponse).isNotNull();
        assertEquals("Login successful", loginResponse.getMessage());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    public void testLogin_NullLoginRequest() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        userService.register(registerRequest);
        User registeredUser = new User();
        registeredUser.setEmail("test@example.com");
        registeredUser.setPassword("password123"); // Store password in plain text
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(registeredUser));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->userService.login(loginRequest));
        assertEquals("LoginRequest cannot be null", exception.getMessage());
    }

    @Test
    public void testLogin_EmptyEmail() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("");
        loginRequest.setPassword("password123");

        userService.register(registerRequest);
        User registeredUser = new User();
        registeredUser.setEmail("test@example.com");
        registeredUser.setPassword("password123"); // Store password in plain text
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(registeredUser));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->userService.login(loginRequest));
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testLogin_NullEmail() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(null);
        loginRequest.setPassword("password123");

        userService.register(registerRequest);
        User registeredUser = new User();
        registeredUser.setEmail("test@example.com");
        registeredUser.setPassword("password123"); // Store password in plain text
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(registeredUser));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->userService.login(loginRequest));
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testLogin_EmptyPassword() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("");

        userService.register(registerRequest);
        User registeredUser = new User();
        registeredUser.setEmail("test@example.com");
        registeredUser.setPassword("password123"); // Store password in plain text
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(registeredUser));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->userService.login(loginRequest));
        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testLogin_NullPassword() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword(null);

        userService.register(registerRequest);
        User registeredUser = new User();
        registeredUser.setEmail("test@example.com");
        registeredUser.setPassword("password123"); // Store password in plain text
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(registeredUser));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->userService.login(loginRequest));
        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testLogin_UserNotFound() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("nonExistingUser@example.com");
        loginRequest.setPassword("password123");

        userService.register(registerRequest);
        User registeredUser = new User();
        registeredUser.setEmail("test@example.com");
        registeredUser.setPassword("password123"); // Store password in plain text
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(registeredUser));
        UserDoesNotExistException exception = assertThrows(UserDoesNotExistException.class, ()->userService.login(loginRequest));
        assertEquals("User does not exist", exception.getMessage());
    }



    @Test
    public void testLogin_InvalidPassword() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("incorrectPassword");

        userService.register(registerRequest);
        User registeredUser = new User();
        registeredUser.setEmail("test@example.com");
        registeredUser.setPassword("password123"); // Store password in plain text
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(registeredUser));
        InvalidUsernameOrPasswordException exception = assertThrows(InvalidUsernameOrPasswordException.class, ()->userService.login(loginRequest));
        assertEquals("Invalid username or password", exception.getMessage());
    }

}