package com.csci5308.medinteract.admin.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void login() throws Exception {
            mockMvc.perform(post("http://localhost:6969/admin/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{ \"adminEmail\": \"admin\",\"adminPassword\": \"Group27\" }"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.msg").value("User logged in Successfully!"))
                    .andExpect(jsonPath("$.isError").value("false"));
    }
}