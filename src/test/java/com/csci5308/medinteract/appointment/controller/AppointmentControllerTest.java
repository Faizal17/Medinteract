package com.csci5308.medinteract.appointment.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void registerAppointment() throws Exception {


        JSONObject obj = new JSONObject();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
//        obj.put("id", 0);
        obj.put("patientId", 12);
        obj.put("doctorId", 37);
        obj.put("colorCode", "#f44437");
        obj.put("startTime", "2023-03-02T22:37:19.703Z");
        obj.put("endTime", "2023-03-02T22:37:19.703Z");
        obj.put("title", "appointment for eye operation");
        obj.put("description", "EYE OPERATION");
        obj.put("active", true);
        String json = obj.toString();

        System.out.println(json);
        mockMvc.perform(post("http://localhost:6969/appointment/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Appointment registered Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));

    }

    @Test
    void fetchAppointmentsByDoctor() throws Exception {

        JSONObject obj = new JSONObject();


        obj.put("patientId", 12);

        String json = obj.toString();

        System.out.println(json);
        mockMvc.perform(post("http://localhost:6969/appointment/fetchAppointmentsByPatient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Appointments fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));
    }

    @Test
    void fetchAppointmentsByPatient() throws Exception {

        JSONObject obj = new JSONObject();


        obj.put("doctorId", 37);

        String json = obj.toString();

        System.out.println(json);
        mockMvc.perform(post("http://localhost:6969/appointment/fetchAppointmentsByDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("Appointments fetched Successfully!"))
                .andExpect(jsonPath("$.isError").value("false"));
    }
}