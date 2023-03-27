package com.csci5308.medinteract.patient.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void fetchAll() throws Exception {
        mockMvc.perform(get("http://localhost:6969/patient/fetchAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("All Patients Fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));
    }


    @Test
    void login() throws Exception {
        JSONObject obj = new JSONObject();


        obj.put("patientEmail", "okabe@gmail.com");
        obj.put("patientPassword", "okabe");

        String json = obj.toString();

        System.out.println(json);
        mockMvc.perform(post("http://localhost:6969/patient/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Patient logged in Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

//    @Test
//    void getPatients() {
//    }

    @Test
    void getPatientById() throws Exception {

        int patientID = 10;
        mockMvc.perform(get("http://localhost:6969/patient/profile/"+patientID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Patient details fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void deletePatientById() {
    }

    @Test
    void registerPatient() {
    }

    @Test
    void updatePatientById() {
    }
}