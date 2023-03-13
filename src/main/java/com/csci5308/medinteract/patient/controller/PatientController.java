package com.csci5308.medinteract.patient.controller;

import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.service.PatientService;
import com.csci5308.medinteract.patient.service.PatientServiceImpl;
import com.csci5308.medinteract.utilities.JWT.JWT;
import com.csci5308.medinteract.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientServiceImpl;
    private final JWT jwtTokenUtil;

    @Autowired
    public PatientController(PatientService patientServiceImpl, JWT jwtTokenUtil, PatientService patientService, PatientServiceImpl patientServiceImp){
        this.patientServiceImpl = patientServiceImpl;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity fetchAll()
    {
        List<PatientModel> patientModelList= patientServiceImpl.fetchAll();

        Response  res = new Response(patientModelList, false, "All Patients Fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity registerPatient(@RequestBody PatientModel patientModel) throws Exception {
        Map<String, Object> data = patientServiceImpl.checkIfEmailExists(patientModel.getPatientEmail());

        if((Boolean)data.get("result"))
        {
            //patient already exists
            Response  res = new Response(null, true, "Patient with email already exists!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);

        }
        else
        {
            if(data.containsKey("id")){
                patientModel.setId((Long)data.get("id"));
            }
            patientModel.setPatientPassword(patientServiceImpl.encodePassword(patientModel.getPatientPassword()));
            patientServiceImpl.savePatient(patientModel);
            patientModel.setPatientPassword("");
            Response res = new Response(patientModel, false, "User registered Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }
    @PostMapping("/login")

    public ResponseEntity login(@RequestBody PatientModel patientModel) throws Exception {
        System.out.println(patientModel.isActive() + patientModel.getPatientPassword());
        if(patientServiceImpl.isPatientValid(patientModel.getPatientEmail(),patientModel.getPatientPassword()))
        {
            patientModel = patientServiceImpl.getPatientByEmail(patientModel.getPatientEmail());
            patientModel.setPatientPassword("");
            Response  res = new Response(jwtTokenUtil.generateToken(patientModel.getPatientEmail(),"patient",patientModel), false, "Patient logged in Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);

        }
        else {
            Response  res = new Response("null", true, "Failed to login for the given credentials");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }

//
//    @GetMapping("/profile")
//    public List<PatientModel> getPatients(){
//        return patientServiceImpl.getPatients();
//    }

    @GetMapping("/profile/{patientId}")
    public ResponseEntity getPatientById(@PathVariable("patientId") Long id){
        Optional<PatientModel> patientModel= patientServiceImpl.getPatientById(id);
        if(patientModel.isEmpty() || patientModel.get().isBlocked() || !patientModel.get().isActive()) {
            Response res = new Response("", true, "Unable to find patient with the given id!");
            return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
        patientModel.get().setPatientPassword("");
        Response  res = new Response(patientModel, false, "Patient details fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{patientId}")
    public ResponseEntity deletePatientById(@PathVariable("patientId") Long id){
        patientServiceImpl.deletePatientById(id);
        Response  res = new Response("", false, "User deleted Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @PostMapping(path = "/updateProfile")
    public ResponseEntity updatePatientById(@RequestBody PatientModel patientModel){
        Optional<PatientModel> optionalPatientModel= patientServiceImpl.getPatientById(patientModel.getId());
        if(optionalPatientModel.isEmpty() || !optionalPatientModel.get().getPatientEmail().equals(patientModel.getPatientEmail()))
        {
            //patient already exists
            Response res = new Response(null, true, "Unable to update profile!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        PatientModel foundPatientModel = optionalPatientModel.get();
        patientModel.setPatientPassword(foundPatientModel.getPatientPassword());
        patientModel.setActive(foundPatientModel.isActive());
        patientModel.setBlocked(foundPatientModel.isBlocked());
        patientServiceImpl.savePatient(patientModel);
        patientModel.setPatientPassword("");
        Response  res = new Response(patientModel, false, "User updated Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }
}
