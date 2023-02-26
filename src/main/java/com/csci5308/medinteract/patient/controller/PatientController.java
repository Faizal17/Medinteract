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
        return new ResponseEntity<>(patientModelList, HttpStatus.OK);
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
            Response  res = new Response(jwtTokenUtil.generateToken(patientModel.getPatientEmail(),"patient",patientModel), false, "User logged in Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);

        }
        else {
            Response  res = new Response("null", true, "Failed to login for the given credentials");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }


    //-------------------------------------
    @GetMapping("/profile")
    public List<PatientModel> getPatients(){
        return patientServiceImpl.getPatients();
    }

    @GetMapping("/profile/{patientId}")
    public Optional<PatientModel> getPatientById(@PathVariable("patientId") Long id){
        return patientServiceImpl.getPatientById(id);
    }

    @DeleteMapping(path = "/{patientId}")
    public void deletePatientById(@PathVariable("patientId") Long id){
        patientServiceImpl.deletePatientById(id);
    }

    @PutMapping(path = "/{patientId}")
    public void updatePatientById(@PathVariable("patientId") Long id,
                                  @RequestParam(required = false) String new_patientName,
                                  @RequestParam(required = false) String new_patientAddressPostalCode,
                                  @RequestParam(required = false) String new_patientAddressStreet,
                                  @RequestParam(required = false) String new_patientMobileNumber
    ){
        patientServiceImpl.updatePatientById(
                id,
                new_patientName,
                new_patientAddressPostalCode,
                new_patientAddressStreet,
                new_patientMobileNumber);
    }
}
