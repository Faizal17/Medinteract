package com.csci5308.medinteract.appointment.service;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import com.csci5308.medinteract.appointment.model.AppointmentModel;

import java.util.List;

public interface AppointmentService {

    AppointmentModel saveAppointment(AppointmentModel appointmentModel);

    List<AppointmentModel> fetchAppointmentsByDoctor(Long doctorId);

    List<AppointmentModel> fetchAppointmentsByPatient(Long patientId);
//    List<DoctorModel> fetchDoctorsByAppointment(Long appointmentId);
}
