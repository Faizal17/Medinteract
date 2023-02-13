package com.csci5308.medinteract.Doctor;

import com.csci5308.medinteract.Doctor.DoctorModel;
import com.csci5308.medinteract.Doctor.DoctorService;
import com.csci5308.medinteract.utilities.JWT;
import com.csci5308.medinteract.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


        if(doctorServiceImpl.checkIfEmailExists(doctorModel.getDoctorEmail()))
        {
            //doctor already exists
            Response res = new Response(null, true, "Doctor with email already exists!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);

        }
        else
        {

            doctorModel.setDoctorPassword(doctorServiceImpl.encodePassword(doctorModel.getDoctorPassword()));
            doctorServiceImpl.saveDoctor(doctorModel);
            Response  res = new Response(doctorModel, false, "Doctor registered Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }
    @GetMapping("/login")

    public ResponseEntity login(@RequestBody DoctorModel doctorModel) throws Exception {

        if(doctorServiceImpl.isDoctorValid(doctorModel.getDoctorEmail(),doctorModel.getDoctorPassword()))
        {
            String jwtToken =  jwtTokenUtil.generateToken(doctorModel.getDoctorEmail());
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
            Response  res = new Response(jwtTokenUtil.extractEmail(token), false, "Token is Valid");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
        else
        {
            Response  res = new Response(jwtTokenUtil.extractEmail(token), true, "Token is InValid");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }


}
