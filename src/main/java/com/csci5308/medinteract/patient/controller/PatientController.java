package com.csci5308.medinteract.patient.controller;

import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.repository.PatientRepository;
import com.csci5308.medinteract.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientServiceImpl;
//    private final PatientRepository patientRepository;

    @Autowired
    public PatientController(PatientService patientServiceImpl){
        this.patientServiceImpl = patientServiceImpl;
//        this.patientRepository = patientRepository;
    }



    @GetMapping("/fetchAll")
    public ResponseEntity fetchAll()
    {
        List<PatientModel> patientModelList= patientServiceImpl.fetchAll();
        return new ResponseEntity<>(patientModelList, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity savePatient(@RequestBody PatientModel patientModel)
    {


        if(patientServiceImpl.checkEmail(patientModel))
        {
            //patient already exists
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Patient Already Exists (CODE 417)\n");

        }
        else
        {
            patientServiceImpl.savePatient(patientModel);
            return new ResponseEntity<>(patientModel, HttpStatus.OK);
        }
    }
//    @PostMapping("/login")
//
//    public String login(@RequestBody PatientModel patientModel) {
//        if(patientServiceImpl.existsByEmailAndPassword(patientModel.getPatientEmail(), patientModel.getPatientPassword())) {
//            return "Logged in Successfully";
//        }
//        return "Patient doesn't exist with the given email id:- " + patientModel.getPatientEmail();
//    }



}
