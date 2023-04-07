package com.csci5308.medinteract.appointment.service;

import com.csci5308.medinteract.doctor.Model.DoctorModel;
import com.csci5308.medinteract.appointment.model.AppointmentModel;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AppointmentService {

    AppointmentModel saveAppointment(AppointmentModel appointmentModel);

    List<AppointmentModel> fetchAppointmentsByDoctor(Long doctorId);

    List<AppointmentModel> fetchAppointmentsByPatient(Long patientId);

    List<AppointmentModel> fetchAppointmentsByPatientAfterDate(Long patientId, LocalDateTime date);

    List<Object> fetchAppointmentsHourly();

    List<Object> fetchAppointmentsDaily();

    List<Object> fetchAppointmentsWithinThreeDays();

    List<Object> fetchAppointmentsWeekly();

    List<DoctorModel> fetchDoctorNamesByPatientsAppointment(Long patientId);
}
