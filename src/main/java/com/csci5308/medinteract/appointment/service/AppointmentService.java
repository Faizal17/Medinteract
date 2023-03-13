package com.csci5308.medinteract.appointment.service;

import com.csci5308.medinteract.appointment.model.AppointmentModel;

import java.util.List;
import java.util.Map;

public interface AppointmentService {

    AppointmentModel saveAppointment(AppointmentModel appointmentModel);

    List<AppointmentModel> fetchAppointmentsByDoctor(Long doctorId);

    List<AppointmentModel> fetchAppointmentsByPatient(Long patientId);

    List<Object> fetchAppointmentsHourly();

    List<Object> fetchAppointmentsDaily();

    List<Object> fetchAppointmentsWithinThreeDays();

    List<Object> fetchAppointmentsWeekly();
}
