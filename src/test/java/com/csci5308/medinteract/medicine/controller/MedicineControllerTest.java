package com.csci5308.medinteract.medicine.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicineControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private String url = "http://localhost:6969/medicine/";

    @Test
    void fetchAll() throws Exception {
        mockMvc.perform(get(url + "fetchAll")).andExpect(status().isOk());
    }

    @Test
    void fetchMedicineById() throws Exception {
        int medicineId = 548;
        mockMvc.perform(get(url + "fetch/" + medicineId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Medicines details fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));
    }
}
