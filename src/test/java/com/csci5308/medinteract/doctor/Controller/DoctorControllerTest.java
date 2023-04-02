package com.csci5308.medinteract.doctor.Controller;

import com.csci5308.medinteract.utilities.TestUtil;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class DoctorControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Test
    void fetchAll() throws Exception {

            mockMvc.perform(get("http://localhost:6969/doctor/fetchAll"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.msg").value("All doctors fetched Successfully!"))
                    .andExpect(jsonPath("$.isError").value("false"));

}

    @Test
    void registerNewDoctor() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("doctorEmail", "ddd@gmail.com");
        obj.put("doctorPassword","abc");

        String json = obj.toString();
        String apiURL = "/doctor/register";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        if(mvcResult.getResponse().getStatus()==200)
        {
            assertFalse(isError);
        }

    }
    @Test
    void registerExistingDoctor() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("doctorEmail", "maulvifaizal@gmail.com");
        obj.put("doctorPassword","1234");

        String json = obj.toString();
        String apiURL = "/doctor/register";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        if(mvcResult.getResponse().getStatus()==200)
        {
            assertTrue(isError);
        }

    }

    @Test
    void login() throws Exception {

        JSONObject obj = new JSONObject();


        obj.put("doctorEmail", "maulvifaizal@gmail.com");
        obj.put("doctorPassword", "1234");

        String json = obj.toString();

        System.out.println(json);
        mockMvc.perform(post("http://localhost:6969/doctor/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor logged in Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void loginWithUnknownUer() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("doctorEmail", "unkown@gmail.com");
        obj.put("doctorPassword","1234");

        String json = obj.toString();
        String apiURL = "/doctor/login";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        if(mvcResult.getResponse().getStatus()==200)
        {
            assertTrue(isError);
        }

    }

    @Test
    void fetchDoctorsOnCity() throws Exception {

        JSONObject obj = new JSONObject();


        obj.put("doctorAddressCity", 1);

        String json = obj.toString();

        System.out.println(json);
        mockMvc.perform(post("http://localhost:6969/doctor/city")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor in City found successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void fetchDoctorsOnProvince() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("doctorEmail", "maulvifaizal@gmail.com");

        String json = obj.toString();
        String apiURL = "/doctor/province";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        if(mvcResult.getResponse().getStatus()==200)
        {
            assertFalse(isError);
        }

    }


    @Test
    void getDoctorById() throws Exception {
        int doctorId = 37;
        mockMvc.perform(get("http://localhost:6969/doctor/profile/"+doctorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor details fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));
    }

    @Test
    void getDoctorUsingUnknownId() throws Exception {
        int doctorId = 377;
        mockMvc.perform(get("/doctor/profile/"+doctorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Unable to find user with the given id!"))
                .andExpect(jsonPath("$.isError").value("true"));
    }


    @Test
    void fetchDoctorsOnName() throws Exception {
        JSONObject obj = new JSONObject();


        obj.put("doctorName", "faizal");

        String json = obj.toString();

        System.out.println(json);
        mockMvc.perform(post("http://localhost:6969/doctor/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor by name found successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void fetchDoctorsOnQualification() throws Exception {
        JSONObject obj = new JSONObject();


        obj.put("doctorQualification", "string");

        String json = obj.toString();

        System.out.println(json);
        mockMvc.perform(post("http://localhost:6969/doctor/qualification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor by qualification found successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));
    }

    @Test
    void deleteDoctorById() throws Exception {

       int doctorID = 11;

        mockMvc.perform(delete("http://localhost:6969/doctor/"+doctorID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("User deleted Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void updateDoctorById() {



    }

    @Test
    void isPending() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/doctor/isPending",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        if (isError) {
            assertEquals("No Pending Doctors!", msg);
        } else {
            assertEquals("Pending Doctors Fetched!", msg);
        }
    }

    @Test
    void isApproved() throws Exception {

        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/doctor/isApproved",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        if (isError) {
            assertEquals("No Approved Doctors!", msg);
        } else {
            assertEquals("Approved Doctors Fetched!", msg);
        }
    }

    @Test
    void isBlocked() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/doctor/isBlocked",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        if (isError) {
            assertEquals("No Blocked Doctors!", msg);
        } else {
            assertEquals("Blocked Doctors Fetched!", msg);
        }
    }

    @Test
    void verifyDoctor() throws Exception {
        String doctorEmail = "tp@gmail.com";
        boolean active= true;

        boolean block = false;

      MvcResult mvcResult =   mockMvc.perform(post("/doctor/verified?doctorEmail="+doctorEmail+"&isActive="+active+"&isBlocked="+block))
                .andReturn();

      assertEquals(mvcResult.getResponse().getStatus(),200);

    }

    @Test
    void blockDoctor() throws Exception {

        String doctorEmail = "tp@gmail.com";

        boolean block = true;

        MvcResult mvcResult =   mockMvc.perform(post("/doctor/blocked?doctorEmail="+doctorEmail+"&isBlocked="+block))
                .andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),200);

    }

    @Test
    void fetchPrescriptionByPatientId() throws Exception {
        long patientId = 12;

        MvcResult mvcResult =   mockMvc.perform(get("/doctor/fetch/"+patientId))
                .andReturn();
        assertEquals(mvcResult.getResponse().getStatus(),200);
    }

    @Test
    void fetchDoctorsOnDetails() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("doctorEmail", "maulvifaizal@gmail.com");

        String json = obj.toString();
        String apiURL = "/doctor/get_doctor_on_doctor_details";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        if(mvcResult.getResponse().getStatus()==200)
        {
            assertFalse(isError);
        }

    }

    @Test
    void findDoctorOnDetailsWithCity() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("doctorEmail", "maulvifaizal@gmail.com");

        String json = obj.toString();
        String apiURL = "/doctor/get_doctor_on_details_and_city";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        if(mvcResult.getResponse().getStatus()==200)
        {
            assertFalse(isError);
        }

    }

    @Test
    void findDoctorOnDetailsWithCityAndFeedback() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("doctorAddressCity", 2);

        String json = obj.toString();
        String apiURL = "/doctor/get_doctor_on_details_and_city_with_feedback";
        MvcResult mvcResult = TestUtil.getResultFromPostAPI(apiURL,json,mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        if(mvcResult.getResponse().getStatus()==200)
        {
            assertFalse(isError);
        }

    }
}
