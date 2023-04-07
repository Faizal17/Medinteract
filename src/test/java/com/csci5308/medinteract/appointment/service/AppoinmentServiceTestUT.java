package com.csci5308.medinteract.appointment.service;

import com.csci5308.medinteract.doctor.Model.DoctorModel;
import com.csci5308.medinteract.appointment.model.AppointmentModel;
import com.csci5308.medinteract.appointment.repository.AppointmentRepository;
import com.csci5308.medinteract.appointment.service.AppointmentServiceImpl;
import com.csci5308.medinteract.feedback.Model.FeedbackModel;
import com.csci5308.medinteract.feedback.Repository.FeedbackRepository;
import com.csci5308.medinteract.feedback.Service.FeedbackServiceImpl;
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

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AppointmentServiceImpl.class)
public class AppoinmentServiceTestUT {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AppointmentRepository appointmentRepository;
    @MockBean
    @Autowired
    private AppointmentRepository mockappointmentRepository;

    @MockBean
    private JWT jwt;

    @Autowired
    private AppointmentServiceImpl appointmentService;


    private AppointmentModel mockAppoinmentModel = new AppointmentModel();;
    private String appoinmentJSON = "{ \"rating\": 3,\"doctorId\": 101, \"patientId\": 201 }";

    @Test
    void saveAppointmentTest() throws Exception {

        Mockito.when(mockappointmentRepository.save(Mockito.any(AppointmentModel.class))).thenReturn(mockAppoinmentModel);

        assertEquals(mockAppoinmentModel,appointmentService.saveAppointment(mockAppoinmentModel));

    }

    @Test
    void fetchAppointmentsByDoctorTest() throws Exception {

        List<AppointmentModel> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);

        Mockito.when(mockappointmentRepository.findByDoctorId(Mockito.anyLong())).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsByDoctor(101l));

    }

    @Test
    void fetchAppointmentsByPatientTest() throws Exception {

        List<AppointmentModel> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);

        Mockito.when(mockappointmentRepository.findByPatientId(Mockito.anyLong())).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsByPatient(101l));

    }

    @Test
    void fetchAppointmentsByPatientAfterDateTest() throws Exception {

        List<AppointmentModel> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);

        LocalDateTime now = LocalDateTime.now();

        Mockito.when(mockappointmentRepository.fetchAppointmentsByPatientAfterDate(Mockito.anyLong(), Mockito.any(LocalDateTime.class))).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsByPatientAfterDate(101l, now));

    }

    @Test
    void fetchAppointmentsHourlyTest() throws Exception {

        DoctorModel doctorModel = new DoctorModel();
        doctorModel.setDoctorEmail("doctor@gmail.com");
        doctorModel.setDoctorPassword("docPass");
        PatientModel patientModel = new PatientModel();
        patientModel.setId(201l);
        patientModel.setPatientEmail("paitent@gamil.com");
        patientModel.setPatientPassword("patientPass");
        List<Object> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);
        mockAppointmentModelList.add(patientModel);
        mockAppointmentModelList.add(doctorModel);


        Mockito.when(mockappointmentRepository.findHourlyAppointments(Mockito.any(LocalDateTime.class))).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsHourly());

    }

    @Test
    void fetchAppointmentsDailyTest() throws Exception {

        DoctorModel doctorModel = new DoctorModel();
        doctorModel.setDoctorEmail("doctor@gmail.com");
        doctorModel.setDoctorPassword("docPass");
        PatientModel patientModel = new PatientModel();
        patientModel.setId(201l);
        patientModel.setPatientEmail("paitent@gamil.com");
        patientModel.setPatientPassword("patientPass");
        List<Object> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);
        mockAppointmentModelList.add(patientModel);
        mockAppointmentModelList.add(doctorModel);


        Mockito.when(mockappointmentRepository.findDailyAppointments(Mockito.any(LocalDateTime.class))).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsDaily());

    }

    @Test
    void fetchAppointmentsWithinThreeDaysTest() throws Exception {

        DoctorModel doctorModel = new DoctorModel();
        doctorModel.setDoctorEmail("doctor@gmail.com");
        doctorModel.setDoctorPassword("docPass");
        PatientModel patientModel = new PatientModel();
        patientModel.setId(201l);
        patientModel.setPatientEmail("paitent@gamil.com");
        patientModel.setPatientPassword("patientPass");
        List<Object> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);
        mockAppointmentModelList.add(patientModel);
        mockAppointmentModelList.add(doctorModel);


        Mockito.when(mockappointmentRepository.findThreeDaysAppointments(Mockito.any(LocalDateTime.class))).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsWithinThreeDays());

    }

    @Test
    void fetchAppointmentsWeeklyTest() throws Exception {

        DoctorModel doctorModel = new DoctorModel();
        doctorModel.setDoctorEmail("doctor@gmail.com");
        doctorModel.setDoctorPassword("docPass");
        PatientModel patientModel = new PatientModel();
        patientModel.setId(201l);
        patientModel.setPatientEmail("paitent@gamil.com");
        patientModel.setPatientPassword("patientPass");
        List<Object> mockAppointmentModelList = new ArrayList<>();
        mockAppointmentModelList.add(mockAppoinmentModel);
        mockAppointmentModelList.add(patientModel);
        mockAppointmentModelList.add(doctorModel);


        Mockito.when(mockappointmentRepository.findWeeklyAppointments(Mockito.any(LocalDateTime.class))).thenReturn(mockAppointmentModelList);

        assertEquals(mockAppointmentModelList,appointmentService.fetchAppointmentsWeekly());

    }

    @Test
    void fetchDoctorNamesByPatientsAppointmentTest() throws Exception {

        DoctorModel doctorModel = new DoctorModel();
        doctorModel.setDoctorEmail("doctor@gmail.com");
        doctorModel.setDoctorPassword("docPass");
        List<DoctorModel> mockDoctorModelList = new ArrayList<>();
        mockDoctorModelList.add(doctorModel);

        Mockito.when(mockappointmentRepository.doctorNameByPatientAppointments(Mockito.anyLong())).thenReturn(mockDoctorModelList);

        assertEquals(mockDoctorModelList,appointmentService.fetchDoctorNamesByPatientsAppointment(201l));

    }
}
