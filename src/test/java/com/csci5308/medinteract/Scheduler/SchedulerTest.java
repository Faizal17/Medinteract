package com.csci5308.medinteract.Scheduler;

import com.csci5308.medinteract.appointment.model.AppointmentModel;
import com.csci5308.medinteract.appointment.repository.AppointmentRepository;
import com.csci5308.medinteract.appointment.service.AppointmentService;
import com.csci5308.medinteract.appointment.service.AppointmentServiceImpl;
import com.csci5308.medinteract.doctor.model.DoctorModel;
import com.csci5308.medinteract.doctor.repository.DoctorRepository;
import com.csci5308.medinteract.notification.service.NotificationService;
import com.csci5308.medinteract.notification.service.NotificationServiceImpl;
import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.repository.PatientRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {Scheduler.class})
@ExtendWith(SpringExtension.class)
class SchedulerTest {
    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private Scheduler scheduler;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private NotificationServiceImpl notificationServiceImpl;

    @InjectMocks
    private AppointmentServiceImpl appointmentServiceImpl;

    private AppointmentModel appointmentModel;
    private PatientModel patientModel;
    private DoctorModel doctorModel;
    private Object[] appointmentData;
    private List<Object[]> appointmentDataList;

    @Before(value = "1")
    public void setup() {
        appointmentModel = new AppointmentModel();
        appointmentModel.setId(1L);
        appointmentModel.setStartTime(LocalDateTime.now());

        patientModel = new PatientModel();
        patientModel.setId(1L);
        patientModel.setPatientName("Rose");

        doctorModel = new DoctorModel();
        doctorModel.setId(1L);
        doctorModel.setDoctorName("Hannah");

        appointmentData = new Object[] {appointmentModel, patientModel, doctorModel};
        appointmentDataList = new ArrayList<>();
        appointmentDataList.add(appointmentData);
    }

    @Test
    void testHourlyScheduler() {
        when(appointmentService.fetchAppointmentsHourly()).thenReturn(new ArrayList<>());
        scheduler.hourlyScheduler();
        verify(appointmentService).fetchAppointmentsHourly();
    }

    @Test
    void testDailyScheduler() {
        when(appointmentService.fetchAppointmentsDaily()).thenReturn(new ArrayList<>());
        scheduler.dailyScheduler();
        verify(appointmentService).fetchAppointmentsDaily();
    }
}

