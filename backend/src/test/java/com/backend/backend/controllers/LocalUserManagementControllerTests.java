package com.backend.backend.controllers;

import com.backend.backend.Controller.LocalUserManagementController;
import com.backend.backend.Entity.LocalUser;
import com.backend.backend.Service.LocalUserManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocalUserManagementController.class)
public class LocalUserManagementControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocalUserManagementService localUserManagementService;

    private static LocalUser testUser;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeAll
    public static void setTestDummies(){
        testUser = new LocalUser("testEmail@gmail.com", "testUser1", "1234");
    }
    @Test
    public void testSuccesfulLogin() throws Exception {
        String goalEmail = testUser.getEmail();
        String goalPassword =  DigestUtils.sha1Hex(testUser.getPassword());
        String testUserJson = objectMapper.writeValueAsString(testUser);
        // Arrange
        when(localUserManagementService.verifyAccount(goalEmail,goalPassword)).thenReturn(testUser);

        // Act & Assert
        mockMvc.perform(get("/api/account/login/{email}/{password}", testUser.getEmail(), testUser.getPassword())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(testUserJson));
    }

    @Test
    public void testFailedLoginWrongEmail() throws Exception {
        String goalEmail = "differentEmail@gmail.com";
        String goalPassword =  DigestUtils.sha1Hex(testUser.getPassword());
        // Arrange
        when(localUserManagementService.verifyAccount(goalEmail,goalPassword)).thenReturn(testUser);

        // Act & Assert
        mockMvc.perform(get("/api/account/login/{email}/{password}", testUser.getEmail(), testUser.getPassword())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
    @Test
    public void testFailedLoginWrongPassword() throws Exception {
        String goalEmail = testUser.getEmail();
        String goalPassword =  DigestUtils.sha1Hex("wrongPassword");
        // Arrange
        when(localUserManagementService.verifyAccount(goalEmail,goalPassword)).thenReturn(testUser);

        // Act & Assert
        mockMvc.perform(get("/api/account/login/{email}/{password}", testUser.getEmail(), testUser.getPassword())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void testFailedLoginWrongUnHashedPassword() throws Exception {
        String goalEmail = testUser.getEmail();
        String goalPassword =  testUser.getPassword();
        // Arrange
        when(localUserManagementService.verifyAccount(goalEmail,goalPassword)).thenReturn(testUser);

        // Act & Assert
        mockMvc.perform(get("/api/account/login/{email}/{password}", testUser.getEmail(), testUser.getPassword())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void testSuccessfulRegister() throws Exception {
        // Arrange
        LocalUser testUserEncrypted = new LocalUser("testEmail@gmail.com", "testUser1", DigestUtils.sha1Hex(testUser.getPassword()));
//        LocalUser testUserEncrypted = new LocalUser("testEmail@gmail.com", "testUser1","0");

        when(localUserManagementService.saveLocalUser(testUserEncrypted)).thenReturn(testUserEncrypted);
        String testUserJson = objectMapper.writeValueAsString(testUser);
        // Act & Assert
        mockMvc.perform(post("/api/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Successful register."));
    }


}