package com.csci5308.medinteract.doctor.Service;

import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.doctor.Controller.DoctorController;
import com.csci5308.medinteract.doctor.Model.DoctorModel;
import com.csci5308.medinteract.doctor.Repository.DoctorRepository;
import com.csci5308.medinteract.doctor.Service.DoctorService;
import com.csci5308.medinteract.feedback.Model.FeedbackModel;
import com.csci5308.medinteract.feedback.Repository.FeedbackRepository;
import com.csci5308.medinteract.feedback.Service.FeedbackServiceImpl;
import com.csci5308.medinteract.province.model.ProvinceModel;
import com.csci5308.medinteract.utilities.JWT.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.dynamic.DynamicType;
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
@WebMvcTest(value = DoctorService.class)
public class DoctorServiceTestUT {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private DoctorRepository doctorRepository;
    @MockBean
    @Autowired
    private DoctorRepository mockDoctorRepository;

    @MockBean
    private JWT jwt;

    @Autowired
    private DoctorServiceImpl doctorService;


    private DoctorModel mockDoctorModel = new DoctorModel("doctor@gmail.com","docPass");;
    private String doctorJSON = "{ \"doctorEmail\": \"doctor@gmail.com\",\"doctorPasswprd\": \"docPass\"}";

    @Test
    void fetchAllTest() throws Exception {

        List<DoctorModel> mockDoctorModelList = new ArrayList<>();
        mockDoctorModelList.add(mockDoctorModel);

        Mockito.when(mockDoctorRepository.findAll()).thenReturn(mockDoctorModelList);

        assertEquals(mockDoctorModelList,doctorService.fetchAll());

    }

    @Test
    void saveDoctorTest() throws Exception {

        Mockito.when(mockDoctorRepository.save(Mockito.any(DoctorModel.class))).thenReturn(mockDoctorModel);

        assertEquals(mockDoctorModel,doctorService.saveDoctor(mockDoctorModel));
    }

    @Test
    void findByIdTest() throws Exception {

        Optional<DoctorModel> mockOptionalDoc = Optional.of(mockDoctorModel);

        Mockito.when(mockDoctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockDoctorModel));

        assertEquals(mockOptionalDoc,doctorService.findById(101l));
    }

    @Test
    void checkIfEmailExistsTest() throws Exception {

        mockDoctorModel.setId(101l);
        Optional<DoctorModel> mockOptionalDoc = Optional.of(mockDoctorModel);
        Mockito.when(mockDoctorRepository.findByDoctorEmail(Mockito.anyString())).thenReturn(mockOptionalDoc);

        Map<String, Object> expectedDocMap = new HashMap<>();
        expectedDocMap.put("result", false);
        expectedDocMap.put("id", 101l);
        expectedDocMap.put("data", mockDoctorModel);

        assertEquals(expectedDocMap, doctorService.checkIfEmailExists("doctor@gmail.com"));
    }

    @Test
    void getDoctorByEmailTest() throws Exception {

        Optional<DoctorModel> mockOptionalDoc = Optional.of(mockDoctorModel);

        Mockito.when(mockDoctorRepository.findByDoctorEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(mockDoctorModel));

        assertEquals(mockDoctorModel,doctorService.getDoctorByEmail("doctor@gmail.com"));
    }

    @Test
    void isDoctorValidTest() throws Exception {

        mockDoctorModel.setActive(true);
        mockDoctorModel.setDoctorPassword("5A/XP6INJb90KlId0ofguA==");
        Optional<DoctorModel> mockOptionalDoc = Optional.of(mockDoctorModel);

        Mockito.when(mockDoctorRepository.findByDoctorEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(mockDoctorModel));

        assertEquals(true,doctorService.isDoctorValid("doctor@gmail.com", "docPass"));
    }

    @Test
    void isDoctorValidFalseTest() throws Exception {

        Optional<DoctorModel> mockOptionalDoc = Optional.of(mockDoctorModel);

        Mockito.when(mockDoctorRepository.findByDoctorEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(mockDoctorModel));

        assertEquals(false,doctorService.isDoctorValid("doctor@gmail.com", "docPass"));
    }

    @Test
    void getDoctorByIdTest() throws Exception {

        Optional<DoctorModel> mockOptionalDoc = Optional.of(mockDoctorModel);

        Mockito.when(mockDoctorRepository.findById(Mockito.anyLong())).thenReturn(mockOptionalDoc);

        assertEquals(mockOptionalDoc,doctorService.getDoctorById(101l));

    }

    @Test
    void deleteDoctorByIdTest() throws Exception {

        Mockito.doNothing().when(mockDoctorRepository).deleteById(Mockito.anyLong());

        doctorService.deleteDoctorById(101l);
    }

    @Test
    void updateDoctorByIdTest() throws Exception {

        List<DoctorModel> mockDoctorModelList = new ArrayList<>();
        mockDoctorModelList.add(mockDoctorModel);

        Mockito.when(mockDoctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockDoctorModel));

        doctorService.updateDoctorById(101l,"doctorName","postalCode","street","123456789");
        //assertEquals(mockDoctorModelList,doctorService.updateDoctorById(101l,"doctorName","postalCode","street","123456789"));

    }

    @Test
    void isPendingTest() throws Exception {

        List<DoctorModel> mockDoctorModelList = new ArrayList<>();
        mockDoctorModelList.add(mockDoctorModel);

        Mockito.when(mockDoctorRepository.findPendingDoctors()).thenReturn(Optional.of(mockDoctorModelList));

        assertEquals(mockDoctorModelList,doctorService.isPending());

    }

    @Test
    void isApprovedTest() throws Exception {

        List<DoctorModel> mockDoctorModelList = new ArrayList<>();
        mockDoctorModelList.add(mockDoctorModel);

        Mockito.when(mockDoctorRepository.findApprovedDoctors()).thenReturn(Optional.of(mockDoctorModelList));

        assertEquals(mockDoctorModelList,doctorService.isApproved());

    }

    @Test
    void isBlockedTest() throws Exception {

        List<DoctorModel> mockDoctorModelList = new ArrayList<>();
        mockDoctorModelList.add(mockDoctorModel);

        Mockito.when(mockDoctorRepository.findBlockedDoctors()).thenReturn(Optional.of(mockDoctorModelList));

        assertEquals(mockDoctorModelList,doctorService.isBlocked());

    }

    @Test
    void verifyDoctorTest() throws Exception {

        Mockito.when(mockDoctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockDoctorModel));
        Mockito.when(mockDoctorRepository.findByDoctorEmail("doctor@gmail.com")).thenReturn(Optional.of(mockDoctorModel));

        doctorService.verifyDoctor("doctor@gmail.com", true, false);

    }

    @Test
    void blockDoctorTest() throws Exception {

        Mockito.when(mockDoctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockDoctorModel));
        Mockito.when(mockDoctorRepository.findByDoctorEmail("doctor@gmail.com")).thenReturn(Optional.of(mockDoctorModel));

        doctorService.blockDoctor("doctor@gmail.com",  false);

    }

    @Test
    void fetchDoctorTest() throws Exception {

        List<DoctorModel> mockDoctorModelList = new ArrayList<>();
        mockDoctorModelList.add(mockDoctorModel);
        Optional<List<DoctorModel>> mockOptionalDoc = Optional.of(mockDoctorModelList);

        Mockito.when(mockDoctorRepository.getDoctorModelById(Mockito.anyLong())).thenReturn(Optional.of(mockDoctorModelList));

        assertEquals(mockOptionalDoc,doctorService.fetchDoctor(101l));
    }

    @Test
    void findDoctorOnDetailsWithCityTest() throws Exception {

        ProvinceModel provinceModel = new ProvinceModel();
        CityModel cityModel = new CityModel();

        mockDoctorModel.setId(101l);
        mockDoctorModel.setDoctorName("doctorName");
        mockDoctorModel.setDoctorAddressProvince(1l);
        mockDoctorModel.setDoctorAddressCity(10l);
        mockDoctorModel.setDoctorQualification("doctorQualification");

        Object[] objectArray = {mockDoctorModel, provinceModel, cityModel};
        List<Object> mockDoctorModelList = new ArrayList<>();
        mockDoctorModelList.add(objectArray);

        Mockito.when(mockDoctorRepository.findDoctorOnDetailsWithCity(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString() )).thenReturn(mockDoctorModelList);


        List<Map<String, Object>> expectedDoctorDetailsList = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("id", 101l);
        data.put("doctorEmail", "doctor@gmail.com");
        data.put("doctorName", "doctorName");
        data.put("doctorAddressProvince", null);
        data.put("doctorAddressCity", null);
        data.put("doctorType", null);
        data.put("doctorQualification", "doctorQualification");
        data.put("profilePhoto", null);

        expectedDoctorDetailsList.add(data);

        assertEquals(expectedDoctorDetailsList, doctorService.findDoctorOnDetailsWithCity(mockDoctorModel));
    }

}