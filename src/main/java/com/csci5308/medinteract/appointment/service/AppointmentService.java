package com.csci5308.medinteract.appointment.service;

import com.csci5308.medinteract.appointment.model.AppointmentModel;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface AppointmentService {

    AppointmentModel saveAppointment(AppointmentModel appointmentModel);

    List<AppointmentModel> fetchAppointmentsByDoctor(Long doctorId);

    List<AppointmentModel> fetchAppointmentsByPatient(Long patientId);

    List<AppointmentModel> fetchAppointmentsByPatientAfterDate(Long patientId, LocalDateTime date);
}
