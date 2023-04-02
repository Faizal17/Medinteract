package com.csci5308.medinteract.doctor.Controller;

import com.csci5308.medinteract.doctor.Model.DoctorModel;
import com.csci5308.medinteract.doctor.Service.DoctorService;
import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import com.csci5308.medinteract.utilities.JWT.JWT;
import com.csci5308.medinteract.utilities.Response;
import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.csci5308.medinteract.utilities.FileUploader.saveFile;

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
        Response res = new Response(doctorModelList, false, "All doctors fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
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
                    , "Doctor logged in Successfully!");
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

//    @GetMapping("/profile")
//    public List<DoctorModel> getAllDoctors(){
//        return doctorServiceImpl.getAllDoctors();
//    }

    @GetMapping("/profile/{doctorId}")
    public ResponseEntity getDoctorById(@PathVariable("doctorId") Long id){
        Optional<DoctorModel> doctorModel = doctorServiceImpl.getDoctorById(id);
        if(doctorModel.isEmpty() || doctorModel.get().isBlocked() || !doctorModel.get().isActive()) {
            Response res = new Response("", true, "Unable to find user with the given id!");
            return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
        doctorModel.get().setDoctorPassword("");
        Response  res = new Response(doctorModel, false, "Doctor details fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
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

    @DeleteMapping(path = "/{doctorId}")
    public ResponseEntity deleteDoctorById(@PathVariable("doctorId") Long id){
        doctorServiceImpl.deleteDoctorById(id);
        Response  res = new Response("", false, "User deleted Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @PostMapping(path = "/updateProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateDoctorById(@RequestParam MultiValueMap<String, String> formData, @RequestParam(value = "profileImage",required = false) MultipartFile profileImage) throws IOException {
        //get doctorModel from Json
        Gson gson = new Gson();
        DoctorModel updatedDoctorModel = gson.fromJson(formData.getFirst("objectData"), DoctorModel.class);
        Optional<DoctorModel> optionalDoctorModel= doctorServiceImpl.getDoctorById(updatedDoctorModel.getId());
        System.out.println(optionalDoctorModel.get());
        if(optionalDoctorModel.isEmpty() || !optionalDoctorModel.get().getDoctorEmail().equals(updatedDoctorModel.getDoctorEmail()))
        {
            //doctor already exists
            Response res = new Response(null, true, "Unable to update profile!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        DoctorModel oldDoctorModel = optionalDoctorModel.get();
        updatedDoctorModel.setDoctorPassword(oldDoctorModel.getDoctorPassword());
        updatedDoctorModel.setActive(oldDoctorModel.isActive());
        updatedDoctorModel.setBlocked(oldDoctorModel.isBlocked());
        if (profileImage != null && !profileImage.isEmpty()) {
            String fileName;
            if (oldDoctorModel.getProfilePicture()!= null) {
                fileName = oldDoctorModel.getProfilePicture().split("/")[2];
            } else {
                fileName = RandomStringUtils.randomAlphanumeric(10) + ".jpeg";
            }
            String uploadDir = "user-photos/profile/";
            try {
                saveFile(uploadDir, fileName, profileImage);
            } catch (IOException e) {
                Response res = new Response("", true, "Error while updating details!");
                return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
            }
            updatedDoctorModel.setProfilePicture(uploadDir + fileName);
        } else {
            updatedDoctorModel.setProfilePicture(oldDoctorModel.getProfilePicture());
        }
        doctorServiceImpl.saveDoctor(updatedDoctorModel);
        updatedDoctorModel.setDoctorPassword("");
        Response  res = new Response(updatedDoctorModel, false, "User updated Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @GetMapping("/isPending")
    public ResponseEntity isPending()
    {
        List<DoctorModel> pendingDoctors = doctorServiceImpl.isPending();
        if(pendingDoctors.isEmpty())
        {
            Response res = new Response(pendingDoctors, true, "No Pending Doctors!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        Response res = new Response(pendingDoctors, false, "Pending Doctors Fetched!", pendingDoctors.size());
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/isApproved")
    public ResponseEntity isApproved()
    {
        List<DoctorModel> approvedDoctors = doctorServiceImpl.isApproved();
        if(approvedDoctors.isEmpty())
        {
            Response res = new Response(approvedDoctors, true, "No Approved Doctors!");
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
            Response res = new Response(blockedDoctors, true, "No Blocked Doctors!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        Response res = new Response(blockedDoctors, false, "Blocked Doctors Fetched!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/verified")
    public ResponseEntity<?> verifyDoctor(@RequestParam(name = "doctorEmail") String email, @RequestParam boolean isActive, @RequestParam boolean isBlocked) {

        doctorServiceImpl.verifyDoctor(email, isActive, isBlocked);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/blocked")
    public ResponseEntity<?> blockDoctor(@RequestParam(name = "doctorEmail") String email, @RequestParam boolean isBlocked) {

        doctorServiceImpl.blockDoctor(email, isBlocked);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/fetch/{patientId}")
    public ResponseEntity fetchPrescriptionByPatientId(@PathVariable("patientId") Long id){
        Optional<List<DoctorModel>> prescriptionModelList = doctorServiceImpl.fetchDoctor(id);
        Response  res = new Response(prescriptionModelList, false, "Doctor details fetched Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }
    @PostMapping("/get_doctor_on_doctor_details")
    public ResponseEntity fetchDoctorsOnDetails(@RequestBody DoctorModel doctorModel)
    {
        List<DoctorModel> doctorModelList = doctorServiceImpl.getDoctorByDetails(doctorModel);
        Response res = new Response(doctorModelList, false, "Doctor by qualification found successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/get_doctor_on_details_and_city")
    public ResponseEntity findDoctorOnDetailsWithCity(@RequestBody DoctorModel doctorModel)
    {
        List<Map<String, Object>> doctorModelList = doctorServiceImpl.findDoctorOnDetailsWithCity(doctorModel);
        Response res = new Response(doctorModelList, false, "Doctor by qualification found successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/get_doctor_on_details_and_city_with_feedback")
    public ResponseEntity findDoctorOnDetailsWithCityAndFeedback(@RequestBody DoctorModel doctorModel)
    {
        List<Map<String, Object>> doctorModelList = doctorServiceImpl.findDoctorOnDetailsWithCityAndFeedback(doctorModel);
        Response res = new Response(doctorModelList, false, "Doctor by qualification found successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }
}