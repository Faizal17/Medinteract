package com.csci5308.medinteract.Doctor.Controller;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import com.csci5308.medinteract.Doctor.Service.DoctorService;
import com.csci5308.medinteract.patient.model.PatientModel;
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
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorServiceImpl;
    private final JWT jwtTokenUtil;

    @Autowired
    public DoctorController(DoctorService doctorServiceImpl, JWT jwtTokenUtil){
        this.doctorServiceImpl = doctorServiceImpl;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity fetchAll()
    {
        List<DoctorModel> doctorModelList= doctorServiceImpl.fetchAll();
        return new ResponseEntity<>(doctorModelList, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity registerDoctor(@RequestBody DoctorModel doctorModel) throws Exception {
        Map<String, Object> data = doctorServiceImpl.checkIfEmailExists(doctorModel.getDoctorEmail());
        if((Boolean)data.get("result"))
        {
            //doctor already exists
            Response res = new Response(null, true, "Doctor with email already exists!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        else
        {
            if(data.containsKey("id")){
                doctorModel.setId((Long)data.get("id"));
            }
            doctorModel.setDoctorPassword(doctorServiceImpl.encodePassword(doctorModel.getDoctorPassword()));
            doctorServiceImpl.saveDoctor(doctorModel);
            doctorModel.setDoctorPassword("");
            Response  res = new Response(doctorModel, false, "User registered Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }
    @PostMapping("/login")

    public ResponseEntity login(@RequestBody DoctorModel doctorModel) throws Exception {

        if(doctorServiceImpl.isDoctorValid(doctorModel.getDoctorEmail(),doctorModel.getDoctorPassword()))
        {
            doctorModel = doctorServiceImpl.getDoctorByEmail(doctorModel.getDoctorEmail());
            doctorModel.setDoctorPassword("");
            Response  res = new Response(jwtTokenUtil.generateToken(doctorModel.getDoctorEmail(),"doctor",doctorModel)
                    , false
                    , "User logged in Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);

        }
        else {
            Response  res = new Response("null", true, "Failed to login for the given credentials");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }

    @GetMapping("/profile")
    public List<DoctorModel> getAllDoctors(){
        return doctorServiceImpl.getAllDoctors();
    }

    @GetMapping("/profile/{doctorId}")
    public ResponseEntity getDoctorById(@PathVariable("doctorId") Long id){
        Optional<DoctorModel> doctorModel = doctorServiceImpl.getDoctorById(id);
        if(doctorModel.isEmpty() || doctorModel.get().isBlocked() || !doctorModel.get().isActive()) {
            Response res = new Response("", true, "Unable to find user with the given id!");
            return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
        doctorModel.get().setDoctorPassword("");
        Response  res = new Response(doctorModel, false, "User details fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{doctorId}")
    public ResponseEntity deleteDoctorById(@PathVariable("doctorId") Long id){
        doctorServiceImpl.deleteDoctorById(id);
        Response  res = new Response("", false, "User deleted Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @PostMapping(path = "/updateProfile")
    public ResponseEntity updateDoctorById(@RequestBody DoctorModel doctorModel){
        Optional<DoctorModel> optionalDoctorModel= doctorServiceImpl.getDoctorById(doctorModel.getId());
        System.out.println(optionalDoctorModel.get());
        if(optionalDoctorModel.isEmpty() || !optionalDoctorModel.get().getDoctorEmail().equals(doctorModel.getDoctorEmail()))
        {
            //doctor already exists
            Response res = new Response(null, true, "Unable to update profile!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        DoctorModel foundDoctorModel = optionalDoctorModel.get();
        doctorModel.setDoctorPassword(foundDoctorModel.getDoctorPassword());
        doctorModel.setActive(foundDoctorModel.isActive());
        doctorModel.setBlocked(foundDoctorModel.isBlocked());
        doctorServiceImpl.saveDoctor(doctorModel);
        doctorModel.setDoctorPassword("");
        Response  res = new Response(doctorModel, false, "User updated Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }
}