package com.csci5308.medinteract.prescription.controller;

import com.csci5308.medinteract.prescription.service.PrescriptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PrescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private String url = "http://localhost:6969/prescription/";

    @MockBean
    private PrescriptionService prescriptionService;

    @Test
    void fetchAll() throws Exception {
        mockMvc.perform(get(url + "fetchAll")).andExpect(status().isOk());
    }

    @Test
    void fetchPrescriptionById() throws Exception {
        int patientId = 44;
        mockMvc.perform(get(url + "fetch/" + patientId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Prescription details fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));
    }
}
