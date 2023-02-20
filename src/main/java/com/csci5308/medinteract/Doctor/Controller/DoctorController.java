package com.csci5308.medinteract.Doctor.Controller;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import com.csci5308.medinteract.Doctor.Service.DoctorService;
import com.csci5308.medinteract.utilities.JWT.JWT;
import com.csci5308.medinteract.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
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


    @GetMapping("/isPending")
    public ResponseEntity isPending()
    {
        List<DoctorModel> pendingDoctors = doctorServiceImpl.isPending();
        if(pendingDoctors.isEmpty())
        {
            Response res = new Response(null, true, "No Pending Doctors!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        Response res = new Response(pendingDoctors, false, "Pending Doctors Fetched!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/isApproved")
    public ResponseEntity isApproved()
    {
        List<DoctorModel> approvedDoctors = doctorServiceImpl.isApproved();
        if(approvedDoctors.isEmpty())
        {
            Response res = new Response(null, true, "No Approved Doctors!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        Response res = new Response(approvedDoctors, false, "Approved Doctors Fetched!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/isBlocked")
    public ResponseEntity isBlocked()
    {
        List<DoctorModel> blockedDoctors = doctorServiceImpl.isBlocked();
        if(blockedDoctors.isEmpty())
        {
            Response res = new Response(null, true, "No Blocked Doctors!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        Response res = new Response(blockedDoctors, false, "Blocked Doctors Fetched!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
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




}
