package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.controller.UserController;
import com.miki.animestylebackend.dto.ChangePasswordRequest;
import com.miki.animestylebackend.dto.UpdateProfileRequest;
import com.miki.animestylebackend.dto.UserData;
import com.miki.animestylebackend.dto.UserDto;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "testUser")
    public void changePasswordSuccess() {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("oldPassword");
        request.setNewPassword("newPassword");

        ResponseEntity<?> response = userController.changePassword(request, null);

        verify(userService, times(1)).changePassword(request, null);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void getUserProfileSuccess() {
        UserDto userDto = new UserDto();
        UserData userData = new UserData();
        userData.setEmail("testUser");
        userDto.setData(userData);

        when(userService.getUserProfile(any(), any())).thenReturn(userDto);

        when(userController.getCurrentUser()).thenReturn(any());

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(any());

        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("testUser");

        ResponseEntity<UserDto> response = userController.getUserProfile();

        verify(userService, times(1)).getUserProfile(any(), any());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("testUser", response.getBody().getData().getEmail());
    }

    @Test
    public void getUserByUsernameSuccess() {
        User user = new User();
        user.setEmail("testUser");

        when(userService.getUserByUsername(anyString())).thenReturn(user);

        ResponseEntity<UserDto> response = userController.getUserByUsername("testUser");

        verify(userService, times(1)).getUserByUsername("testUser");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("testUser", response.getBody().getData().getEmail());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void saveUserSuccess() {
        UpdateProfileRequest profile = new UpdateProfileRequest();
        UserDto userDto = new UserDto();
        UserData userData = new UserData();
        userData.setEmail("testUser");
        userDto.setData(userData);

        when(userService.save(any(), any())).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.saveUser(profile);

        verify(userService, times(1)).save(any(), any());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("testUser", response.getBody().getData().getEmail());

    }
}