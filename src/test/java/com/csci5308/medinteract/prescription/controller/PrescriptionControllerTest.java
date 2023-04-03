package com.csci5308.medinteract.prescription.controller;

import com.csci5308.medinteract.utilities.TestUtil;
import io.jsonwebtoken.impl.crypto.MacValidator;
import io.swagger.v3.core.util.Json;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PrescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void addPrescription() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("doctorId",37);
        obj.put("patientId",12);
        String json = obj.toString();
        MvcResult mvcResult = TestUtil.getResultFromPostAPI("/prescription/addPrescription",json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void fetchAll() throws Exception {

        MvcResult mvcResult  = mockMvc.perform(get("/prescription/fetchAll")).andReturn();
        assertEquals(200,mvcResult.getResponse().getStatus());

    }

    @Test
    void fetchPrescriptionByPatientId() throws Exception {
        int patientID = 1;
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/prescription/fetch/"+patientID,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }
}