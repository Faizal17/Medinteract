package com.csci5308.medinteract.Doctor.Controller;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import com.csci5308.medinteract.Doctor.Service.DoctorService;
import com.csci5308.medinteract.utilities.JWT.JWT;
import com.csci5308.medinteract.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/city")
    public ResponseEntity fetchDoctorsOnCity(@RequestBody DoctorModel doctorModel)
    //public ResponseEntity fetchDoctorsOnCity(String city)
    {
        List<DoctorModel> doctorModelList = doctorServiceImpl.fetchDoctorsOnCity(doctorModel);
        Response res = new Response(doctorModelList, false, "Doctor in City found successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }
    @PostMapping("/province")
    public ResponseEntity fetchDoctorsOnProvince(@RequestBody DoctorModel doctorModel)
    {
        List<DoctorModel> doctorModelList = doctorServiceImpl.fetchDoctorsOnProvince(doctorModel);
        Response res = new Response(doctorModelList, false, "Doctor in Province  found successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }


    @PostMapping("/name")
    public ResponseEntity fetchDoctorsOnName(@RequestBody DoctorModel doctorModel)
    {
        List<DoctorModel> doctorModelList = doctorServiceImpl.fetchDoctorsOnName(doctorModel);
        Response res = new Response(doctorModelList, false, "Doctor by name found successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }


    @PostMapping("/qualification")
    public ResponseEntity fetchDoctorsOnQualification(@RequestBody DoctorModel doctorModel)
    {
        List<DoctorModel> doctorModelList = doctorServiceImpl.fetchDoctorsOnQualification(doctorModel);
        Response res = new Response(doctorModelList, false, "Doctor by qualification found successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

}
