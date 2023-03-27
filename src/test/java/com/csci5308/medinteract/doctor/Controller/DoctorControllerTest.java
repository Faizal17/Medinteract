package com.csci5308.medinteract.doctor.Controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
    void registerDoctor() {


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
    void getDoctorById() throws Exception {
        int doctorId = 37;
        mockMvc.perform(get("http://localhost:6969/doctor/profile/"+doctorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Doctor details fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));
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
                .andExpect(jsonPath("$.isError").value("false"));;

    }

    @Test
    void updateDoctorById() {



    }

    @Test
    void isPending() throws Exception {


        MvcResult result = mockMvc.perform(get("http://localhost:6969/doctor/isPending"))
                .andExpect(status().isOk())
                .andReturn();
//        String content = result.getResponse().getContentAsString();
//        JSONObject json = new JSONObject(content);
//        if (Objects.equals(json.get("data").toString(), "[]"))
//        {
//        }


//                .andExpect(jsonPath("$.msg").value("Pending Doctors Fetched!"))
//                .andExpect(jsonPath("$.isError").value("false"));
    }

    @Test
    void isApproved() throws Exception {


//        mockMvc.perform(get("http://localhost:6969/doctor/isApproved"))
        mockMvc.perform(get("http://localhost:6969/doctor/isApproved"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Approved Doctors Fetched!"))
                .andExpect(jsonPath("$.isError").value("false"));
    }

    @Test
    void isBlocked() throws Exception {
        mockMvc.perform(get("http://localhost:6969/doctor/isBlocked"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Blocked Doctors Fetched!"))
                .andExpect(jsonPath("$.isError").value("false"));
    }

    @Test
    void verifyDoctor() throws Exception {
        String doctorEmail = "tp@gmail.com";
        boolean active= true;

        boolean block = false;
        http://localhost:6969/doctor/verified?doctorEmail=tp%40gmail.com&isActive=true&isBlocked=false

        mockMvc.perform(post("http://localhost:6969/doctor/verified?doctorEmail="+doctorEmail+"&isActive="+active+"&isBlocked="+block))
                .andExpect(status().isOk());

    }

    @Test
    void blockDoctor() throws Exception {

        String doctorEmail = "tp@gmail.com";

        boolean block = true;

        mockMvc.perform(post("http://localhost:6969/doctor/blocked?doctorEmail=t"+doctorEmail+"&isBlocked="+block))
                .andExpect(status().isOk());
    }
}
