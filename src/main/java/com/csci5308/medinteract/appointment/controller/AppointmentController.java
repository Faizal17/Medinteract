package com.csci5308.medinteract.appointment.controller;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import com.csci5308.medinteract.appointment.model.AppointmentModel;
import com.csci5308.medinteract.appointment.service.AppointmentServiceImpl;
import com.csci5308.medinteract.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentServiceImpl appointmentServiceImpl;

    @Autowired
    public AppointmentController(AppointmentServiceImpl appointmentServiceImpl){
        this.appointmentServiceImpl = appointmentServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity registerAppointment(@RequestBody AppointmentModel appointmentModel) throws Exception {
        appointmentServiceImpl.saveAppointment(appointmentModel);
        Response res = new Response(appointmentModel, false, "Appointment registered Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @PostMapping("/fetchAppointmentsByDoctor")
    public ResponseEntity fetchAppointmentsByDoctor(@RequestBody AppointmentModel appointmentModel) {
        List<AppointmentModel> appointmentModelList = appointmentServiceImpl.fetchAppointmentsByDoctor(appointmentModel.getDoctorId());
        Response res = new Response(appointmentModelList, false, "Appointments fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @PostMapping("/fetchAppointmentsByPatient")
    public ResponseEntity fetchAppointmentsByPatient(@RequestBody AppointmentModel appointmentModel) {
        List<AppointmentModel> appointmentModelList = appointmentServiceImpl.fetchAppointmentsByPatient(appointmentModel.getPatientId());
        Response res = new Response(appointmentModelList, false, "Appointments fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }
}
