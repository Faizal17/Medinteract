package com.csci5308.medinteract.appointment.service;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import com.csci5308.medinteract.appointment.model.AppointmentModel;
import com.csci5308.medinteract.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public AppointmentModel saveAppointment(AppointmentModel appointmentModel) {
        return appointmentRepository.save(appointmentModel);
    }

    @Override
    public List<AppointmentModel> fetchAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<AppointmentModel> fetchAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public List<Object> fetchAppointmentsHourly() {
        return appointmentRepository.findHourlyAppointments(LocalDateTime.now());
    }

    @Override
    public List<Object> fetchAppointmentsDaily() {
        System.out.println(LocalDateTime.now());
        return appointmentRepository.findDailyAppointments(LocalDateTime.now());
    }

    @Override
    public List<Object> fetchAppointmentsWithinThreeDays() {
        return appointmentRepository.findThreeDaysAppointments(LocalDateTime.now());
    }

    @Override
    public List<Object> fetchAppointmentsWeekly() {
        return appointmentRepository.findWeeklyAppointments(LocalDateTime.now());
    }

    @Override
    public List<DoctorModel> fetchDoctorNamesByPatientsAppointment(Long patientId) {
        return appointmentRepository.doctorNameByPatientAppointments(patientId);
    }

}