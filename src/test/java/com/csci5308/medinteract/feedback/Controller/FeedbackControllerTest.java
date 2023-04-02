package com.csci5308.medinteract.feedback.Controller;

import com.csci5308.medinteract.utilities.TestUtil;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test

        void fetchAll() throws Exception {

        MvcResult mvcResult =  TestUtil.getResultFromGetAPI("/feedback/fetchAll",mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }


    @Test
    void saveFeedback() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("patientId", 12);
        obj.put("doctorId", 37);
        String json = obj.toString();
        MvcResult mvcResult =  TestUtil.getResultFromPostAPI("/feedback/saveFeedback",json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void fetchFeedbackByDoctorId() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("doctorId", 37);
        String json = obj.toString();
        MvcResult mvcResult =  TestUtil.getResultFromPostAPI("/feedback/fetchFeedback_by_doctorId",json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void fetchFeedbackByDoctorIdAndPatient() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("patientId", 12);
        obj.put("doctorId", 37);
        String json = obj.toString();
        MvcResult mvcResult =  TestUtil.getResultFromPostAPI("/feedback/fetchFeedback_by_doctorId_and_patient",json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);

    }

    @Test
    void deleteAll() throws Exception {

         mockMvc.perform(put("/feedback/deleteAll"))
                 .andExpect(status().isOk())
                 .andReturn();
    }

    @Test
    void findAvgRatingOfDoctor() throws Exception {
        MvcResult mvcResult =  TestUtil.getResultFromGetAPI("/feedback/fetchAvgFeedback",mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }
}