package com.csci5308.medinteract.patient.controller;

import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.service.PatientService;
import com.csci5308.medinteract.utilities.JWT;
import com.csci5308.medinteract.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientServiceImpl;
    private final JWT jwtTokenUtil;


    @Autowired
    public PatientController(PatientService patientServiceImpl, JWT jwtTokenUtil){
        this.patientServiceImpl = patientServiceImpl;
        this.jwtTokenUtil = jwtTokenUtil;
    }





    @GetMapping("/fetchAll")
    public ResponseEntity fetchAll()
    {
        List<PatientModel> patientModelList= patientServiceImpl.fetchAll();
        return new ResponseEntity<>(patientModelList, HttpStatus.OK);
    }



    @PostMapping("/register")
    public ResponseEntity registerPatient(@RequestBody PatientModel patientModel) throws Exception {


        if(patientServiceImpl.checkIfEmailExists(patientModel.getPatientEmail()))
        {
            //patient already exists
            Response  res = new Response(null, true, "Patient with email already exists!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);

        }
        else
        {

            patientModel.setPatientPassword(patientServiceImpl.encodePassword(patientModel.getPatientPassword()));
            patientServiceImpl.savePatient(patientModel);
            Response  res = new Response(patientModel, false, "Patient registered Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }
    @GetMapping("/login")

    public ResponseEntity login(@RequestBody PatientModel patientModel) throws Exception {

        if(patientServiceImpl.isPatientValid(patientModel.getPatientEmail(),patientModel.getPatientPassword()))
        {
            String jwtToken =  jwtTokenUtil.generateToken(patientModel.getPatientEmail());
            Response  res = new Response(jwtToken, false, "Token Created Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);

        }
        else {
            Response  res = new Response("null", true, "Failed to generate the token");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }

    @GetMapping("/validateJWTToken")
    public ResponseEntity validateJWTToken(@RequestBody String token)
    {
        if(!token.isEmpty() && jwtTokenUtil.validateToken(token))
        {
            Response  res = new Response(jwtTokenUtil.extractEmail(token), false, "Token in Valid");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
        else
        {
            Response  res = new Response(jwtTokenUtil.extractEmail(token), true, "Token in InValid");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }



}