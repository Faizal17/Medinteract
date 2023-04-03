package com.csci5308.medinteract.admin.Controller;

import com.csci5308.medinteract.admin.Model.AdminModel;
import com.csci5308.medinteract.admin.Service.AdminService;
import com.csci5308.medinteract.utilities.JWT.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;

import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private AdminController adminController;

    @MockBean
    private AdminService adminService;

    @MockBean
    private JWT jWT;

    @Test
    void testLoginTrue() throws Exception {
        when(adminService.isAdminValid(Mockito.<String>any(), Mockito.<String>any())).thenReturn(true);
        when(jWT.generateToken(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenReturn(new HashMap<>());

        AdminModel adminModel = new AdminModel();
        adminModel.setActive(true);
        adminModel.setAdminEmail("memo@dal.ca");
        adminModel.setAdminPassword("memo");
        String content = (new ObjectMapper()).writeValueAsString(adminModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"msg\":\"User logged in Successfully!\",\"isError\":false,\"data\":{}}"));
    }

    @Test
    void testLoginFalse() throws Exception {
        when(adminService.isAdminValid(Mockito.<String>any(), Mockito.<String>any())).thenReturn(false);
        when(jWT.generateToken(Mockito.<String>any(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenReturn(new HashMap<>());

        AdminModel adminModel = new AdminModel();
        adminModel.setActive(true);
        adminModel.setAdminEmail("memo@dal.ca");
        adminModel.setAdminPassword("memo");
        String content = (new ObjectMapper()).writeValueAsString(adminModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"msg\":\"Admin log-in Failed!\",\"isError\":true,\"data\":\"null\"}"));
    }
}