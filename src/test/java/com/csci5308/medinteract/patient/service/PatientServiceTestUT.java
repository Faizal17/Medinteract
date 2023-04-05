package com.csci5308.medinteract.patient.service;

import com.csci5308.medinteract.doctor.Model.DoctorModel;
import com.csci5308.medinteract.doctor.Repository.DoctorRepository;
import com.csci5308.medinteract.doctor.Service.DoctorServiceImpl;
import com.csci5308.medinteract.patient.controller.PatientController;
import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.repository.PatientRepository;
import com.csci5308.medinteract.patient.service.PatientService;
import com.csci5308.medinteract.patient.service.PatientServiceImpl;
import com.csci5308.medinteract.utilities.JWT.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvcExtensionsKt;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PatientService.class)
public class PatientServiceTestUT {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private PatientRepository patientRepository;
    @MockBean
    @Autowired
    private PatientRepository mockPatientRepository;

    @MockBean
    private JWT jwt;

    @Autowired
    private PatientServiceImpl patientService;

    private PatientModel mockPatientModel = new PatientModel(201l,"patient@gmail.com","patientPass");;
    private String patientJSON = "{ \"patientEmail\": \"patient@gmail.com\",\"patientPassword\": \"patientPass\" }";

    @Test
    void fetchAllTest() throws Exception {

        List<PatientModel> mockPatientModelList = new ArrayList<>();
        mockPatientModelList.add(mockPatientModel);

        Mockito.when(mockPatientRepository.findAll()).thenReturn(mockPatientModelList);

        assertEquals(mockPatientModelList,patientService.fetchAll());

    }

    @Test
    void savePatientTest() throws Exception {

        Mockito.when(mockPatientRepository.save(Mockito.any(PatientModel.class))).thenReturn(mockPatientModel);

        assertEquals(mockPatientModel,patientService.savePatient(mockPatientModel));
    }

    @Test
    void findByIdTest() throws Exception {

        Optional<PatientModel> mockOptionalPatient = Optional.of(mockPatientModel);

        Mockito.when(mockPatientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockPatientModel));

        assertEquals(mockOptionalPatient,patientService.findById(201l));
    }

    @Test
    void checkIfEmailExistsTest() throws Exception {

        mockPatientModel.setId(201l);
        Optional<PatientModel> mockOptionalDoc = Optional.of(mockPatientModel);
        Mockito.when(mockPatientRepository.findByPatientEmail(Mockito.anyString())).thenReturn(mockOptionalDoc);

        Map<String, Object> expectedDocMap = new HashMap<>();
        expectedDocMap.put("result", false);
        expectedDocMap.put("id", 201l);

        assertEquals(expectedDocMap, patientService.checkIfEmailExists("patient@gmail.com"));
    }

    @Test
    void getDoctorByEmailTest() throws Exception {

        Optional<PatientModel> mockOptionalPatient = Optional.of(mockPatientModel);

        Mockito.when(mockPatientRepository.findByPatientEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(mockPatientModel));

        assertEquals(mockPatientModel,patientService.getPatientByEmail("patient@gmail.com"));
    }

    @Test
    void isPatientValidTest() throws Exception {

        mockPatientModel.setActive(true);
        mockPatientModel.setBlocked(false);
        mockPatientModel.setPatientPassword("tapUKAaVX7qtDD44vBPT8Q==");
        Optional<PatientModel> mockOptionalDoc = Optional.of(mockPatientModel);

        Mockito.when(mockPatientRepository.findByPatientEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(mockPatientModel));

        assertEquals(true,patientService.isPatientValid("patient@gmail.com", "patientPass"));
    }

    @Test
    void isDoctorValidFalseTest() throws Exception {

        Optional<PatientModel> mockOptionalDoc = Optional.of(mockPatientModel);

        Mockito.when(mockPatientRepository.findByPatientEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(mockPatientModel));

        assertEquals(false,patientService.isPatientValid("patient@gmail.com", "patientPass"));
    }

    @Test
    void getPatientByIdTest() throws Exception {

        Optional<PatientModel> mockOptionalPatient = Optional.of(mockPatientModel);

        Mockito.when(mockPatientRepository.findById(Mockito.anyLong())).thenReturn(mockOptionalPatient);

        assertEquals(mockOptionalPatient,patientService.getPatientById(201l));
    }

    @Test
    void deletePatientByIdTest() throws Exception {

        Mockito.doNothing().when(mockPatientRepository).deleteById(Mockito.anyLong());

        patientService.deletePatientById(201l);
    }

    @Test
    void updatePatientByIdTest() throws Exception {

        List<PatientModel> mockPatientModelList = new ArrayList<>();
        mockPatientModelList.add(mockPatientModel);

        Mockito.when(mockPatientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockPatientModel));

        patientService.updatePatientById(101l,"patientName","postalCode","street","123456789");
        //assertEquals(mockDoctorModelList,doctorService.updateDoctorById(101l,"doctorName","postalCode","street","123456789"));

    }

    @Test
    void fetchPatientsWithAppointmentTest() throws Exception {

        List<PatientModel> mockPatientModelList = new ArrayList<>();
        mockPatientModelList.add(mockPatientModel);
        Optional<List<PatientModel>> mockOptionalPatient = Optional.of(mockPatientModelList);


        Mockito.when(mockPatientRepository.findByPatientId(Mockito.anyLong())).thenReturn(mockOptionalPatient);

        assertEquals(mockOptionalPatient,patientService.fetchPatientsWithAppointment(201l));
    }
}