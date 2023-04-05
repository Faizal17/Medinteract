package com.csci5308.medinteract.feedback.Service;

import com.csci5308.medinteract.doctor.Model.DoctorModel;
import com.csci5308.medinteract.feedback.Model.FeedbackModel;
import com.csci5308.medinteract.feedback.Repository.FeedbackRepository;
import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.utilities.JWT.JWT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = FeedbackServiceImpl.class)
public class FeedbackServiceTestUT {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private FeedbackRepository feedbackRepository;
    @MockBean
    @Autowired
    private FeedbackRepository mockfeedbackRepository;

    @MockBean
    private JWT jwt;

    @Autowired
    private FeedbackServiceImpl feebackService;


    private FeedbackModel mockFeedbackModel = new FeedbackModel(3,101,201);;
    private String feedbackJSON = "{ \"rating\": 3,\"doctorId\": 101, \"patientId\": 201 }";

    @Test
    void saveFeedbackTest() throws Exception {

        //Mockito.doNothing().doThrow(new RuntimeException()).when(feedbackRepository).save(Mockito.any(FeedbackModel.class));

        Mockito.when(feedbackRepository.save(Mockito.any(FeedbackModel.class))).thenReturn(mockFeedbackModel);
        feebackService.saveFeedback(mockFeedbackModel);

    }

    @Test
    void fetchFeedbackByDoctorIdAndPatientTest() throws Exception {

        //Mockito.doNothing().doThrow(new RuntimeException()).when(feedbackRepository).save(Mockito.any(FeedbackModel.class));

        List<Map<String, Object>> mockFeedbackDetailsList = new ArrayList<>();
        Map<String, Object> mockData = new HashMap<>();
        mockFeedbackDetailsList.add(mockData);

        Mockito.when(feedbackRepository.findByDoctorIdAndPatientId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(mockFeedbackModel);
        //Mockito.when(feedbackRepository.findByDoctorIdAndPatient(Mockito.anyLong())).thenReturn((List<Object>) mockFeedbackModel);


        assertEquals(mockFeedbackDetailsList,feebackService.fetchFeedbackByDoctorIdAndPatient(mockFeedbackModel));

    }

    @Test
    void fetchFeedbackByDoctorIdAndPatientFoudTest() throws Exception {

        //Mockito.doNothing().doThrow(new RuntimeException()).when(feedbackRepository).save(Mockito.any(FeedbackModel.class));

        List<Map<String, Object>> mockFeedbackDetailsList = new ArrayList<>();
        Map<String, Object> mockData = new HashMap<>();
        mockFeedbackDetailsList.add(mockData);

        PatientModel mockPatientModel = new PatientModel(201l,"patient@gmail.com","patientPass");

        Object[] objectArray = {mockFeedbackModel, mockPatientModel};
        List<Object> mockFeedbackModelList = new ArrayList<>();
        mockFeedbackModelList.add(objectArray);


        mockFeedbackModel.setId(1001l);
        mockFeedbackModel.setComment("ok");

        Mockito.when(mockfeedbackRepository.findByDoctorIdAndPatientId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(mockFeedbackModel);
        Mockito.when(mockfeedbackRepository.findByDoctorIdAndPatient(Mockito.anyLong())).thenReturn(mockFeedbackModelList);


        Map<String, Object> data = new HashMap<>();
        mockFeedbackDetailsList = new ArrayList<>();
        data.put("id", 1001l);
        data.put("feedbackDate", null);
        data.put("rating", 3l);
        data.put("comment", "ok");
        data.put("doctorId", 101l);
        data.put("patientId", 201l);

        mockFeedbackDetailsList.add(data);
        data = new HashMap<>();

        data.put("id", 1001l);
        data.put("feedbackDate", null);
        data.put("rating", 3l);
        data.put("comment", "ok");
        data.put("doctorId", 101l);
        data.put("patientId", 201l);
        data.put("patientName", null);
        data.put("profilePicture", null);

        mockFeedbackDetailsList.add(data);
        List<Map<String, Object>> actualList = feebackService.fetchFeedbackByDoctorIdAndPatient(mockFeedbackModel);


        Map<String, Object> actualMap = new HashMap<>();
        Map<String, Object> expectedMap = new HashMap<>();

        actualMap = actualList.get(1);
        expectedMap = mockFeedbackDetailsList.get(1);
        assertEquals(mockFeedbackDetailsList,actualList);
        //assertEquals(expectedMap.get("doctorId"),actualMap.get("doctorId"));

    }

    @Test
    void findAvgRatingOfDoctorTest() throws Exception {

        DoctorModel mockDoctorModel = new DoctorModel("doctor@gmail.com","docPass");

        Object[] objectArray = {101l, 3d};
        List<Object> mockFeedbackModelList = new ArrayList<>();
        mockFeedbackModelList.add(objectArray);

        Mockito.when(mockfeedbackRepository.findAvgRatingOfDoctor()).thenReturn(mockFeedbackModelList);


        List<Map<String, Object>> expectedFeedbackDetailsList = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        data.put("doctorId", 101l);
        data.put("avgRating", 3d);
        expectedFeedbackDetailsList.add(data);

        assertEquals(expectedFeedbackDetailsList, feebackService.findAvgRatingOfDoctor());
    }
}


