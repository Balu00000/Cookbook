package com.maven.cookbook.service;

import com.maven.cookbook.model.User;
import com.maven.cookbook.repository.UserRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserRepository mockRepo;
    private UserService service;

    public UserServiceTest() {
    }

    @BeforeEach
    public void setUp() {
        mockRepo = mock(UserRepository.class);

        service = new UserService() {
        {
            layer = mockRepo;
        }};
    }

    @Test
    public void testLoginSuccess() {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("johndoe");
        mockUser.setEmail("john@example.com");
        mockUser.setIsAdmin(false);
        mockUser.setIsDeleted(false);

        when(mockRepo.login("john@example.com", "Password123!")).thenReturn(
                mockUser);

        JSONObject response = service.login("john@example.com", "Password123!");
        assertEquals("success", response.getString("status"));
        assertEquals(200, response.getInt("statusCode"));
        assertNotNull(response.getJSONObject("result").getString("jwt"));
    }

    @Test
    public void testUserNotFound() {
        User emptyUser = new User();
        when(mockRepo.login("john@example.com", "Password123!")).thenReturn(
                emptyUser);

        JSONObject response = service.login("john@example.com", "Password123!");
        assertEquals("userNotFound", response.getString("status"));
        assertEquals(404, response.getInt("statusCode"));
    }

    @Test
    public void testModelException() {
        when(mockRepo.login("john@example.com", "Password123!"))
                .thenReturn(null);

        JSONObject response = service.login("john@example.com", "Password123!");
        assertEquals("modelException", response.getString("status"));
        assertEquals(500, response.getInt("statusCode"));
    }

    @Test
    public void testInvalidEmail() {
        JSONObject response = service.login("thisIsNotAnEmail", "Password123!");
        assertEquals("InvalidEmail", response.getString("status"));
        assertEquals(417, response.getInt("statusCode"));
    }
    
    @Test
    public void testInvalidPassword() {
        User u = new User();
        u.setEmail("user@example.com");
        u.setPassword("123");

        JSONObject response = service.registerUser(u);
        assertEquals("InvalidPassword", response.getString("status"));
        assertEquals(417, response.getInt("statusCode"));
    }

    @Test
    public void testDeletedUser() {
        User deletedUser = new User();
        deletedUser.setId(2);
        deletedUser.setIsDeleted(true);

        when(mockRepo.login("deleted@example.com", "Password123!")).thenReturn(
                deletedUser);

        JSONObject response = service.login("deleted@example.com",
                "Password123!");
        assertEquals("deletedUser", response.getString("status"));
        assertEquals(404, response.getInt("statusCode"));
    }
    
    @Test
    public void testUserExistsModelException() {
        User u = new User();
        u.setEmail("user@example.com");
        u.setPassword("StrongPass1!");

        mockStatic(UserRepository.class);
        when(UserRepository.isUserExists(u.getEmail())).thenReturn(null);

        JSONObject response = service.registerUser(u);
        assertEquals("ModelException", response.getString("status"));
        assertEquals(500, response.getInt("statusCode"));
    }
    
    @Test
    public void testRegisterSuccess() {
        User u = new User();
        u.setEmail("user@example.com");
        u.setPassword("StrongPass1!");

        when(UserRepository.isUserExists(u.getEmail())).thenReturn(false);
        when(mockRepo.registerUser(u)).thenReturn(true);

        JSONObject response = service.registerUser(u);
        assertEquals("success", response.getString("status"));
        assertEquals(200, response.getInt("statusCode"));
    }
}
