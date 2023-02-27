package com.csci5308.medinteract.Doctor.Service;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import com.csci5308.medinteract.Doctor.Repository.DoctorRepository;
import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.utilities.PasswordEncodeDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<DoctorModel> fetchAll() {
        return doctorRepository.findAll();
    }

    @Override
    public DoctorModel saveDoctor(DoctorModel doctorModel) {
        return doctorRepository.save(doctorModel);
    }

    @Override
    public Optional<DoctorModel> findById(Long id) {
        return  doctorRepository.findById(id);
    }



    @Override
    public Map<String, Object> checkIfEmailExists(String email) {
        Map<String, Object> res = new HashMap<>();
        Optional<DoctorModel> newDoctor = doctorRepository.findByDoctorEmail(email);
        boolean result = newDoctor.isPresent() && (newDoctor.get().isActive() || newDoctor.get().isBlocked());
        res.put("result", result);
        if(newDoctor.isPresent()){
            res.put("id", newDoctor.get().getId());
            res.put("data", newDoctor.get());
        }
        return res;
    }

    @Override
    public DoctorModel getDoctorByEmail(String email) {
        Optional<DoctorModel> newDoctor = doctorRepository.findByDoctorEmail(email);
        return newDoctor.orElse(null);
    }

    @Override
    public boolean isDoctorValid(String doctorEmail, String doctorPassword) throws Exception {
        Optional<DoctorModel> doctor = doctorRepository.findByDoctorEmail(doctorEmail);

        String encodedPassword = encodePassword(doctorPassword);
        if(doctor.isPresent() && doctor.get().getDoctorPassword().equals(encodedPassword) && doctor.get().isActive() && !doctor.get().isBlocked())
        {
            //valid doctor
            System.out.println(doctor.get().getDoctorEmail());
            System.out.println(doctor.get().getDoctorPassword());
            return true;
        }
        return false;
    }

    public String encodePassword(String password) throws Exception {
        System.out.println("password to encode : "+password);
        return PasswordEncodeDecode.encrypt(password);
    }

    public List<DoctorModel> fetchDoctorsOnCity(DoctorModel doctorModel)
    {
        Long cityId = doctorModel.getDoctorAddressCity();
        return doctorRepository.findByDoctorAddressCity(cityId);
    }

    public List<DoctorModel> fetchDoctorsOnProvince(DoctorModel doctorModel)
    {
        Long provinceId = doctorModel.getDoctorAddressProvince();
        return doctorRepository.findByDoctorAddressProvince(provinceId);
    }


    public List<DoctorModel> fetchDoctorsOnName(DoctorModel doctorModel)
    {
        String name = doctorModel.getDoctorName();
        return doctorRepository.findByDoctorNameContaining(name);
    }


    public List<DoctorModel> fetchDoctorsOnQualification(DoctorModel doctorModel)
    {
        String qualification = doctorModel.getDoctorQualification();
        return doctorRepository.findByDoctorQualification(qualification);
    }

    @Override
    public List<DoctorModel> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<DoctorModel> getDoctorById(Long id){
        return doctorRepository.findById(id);
    }

    @Override
    public void deleteDoctorById(Long id){
        doctorRepository.deleteById(id);
    }

    @Transactional
    public void updateDoctorById(
            Long id,
            String new_doctorName,
            String new_doctorAddressPostalCode,
            String new_doctorAddressStreet,
            String new_doctorMobileNumber) {

        DoctorModel doctorModel = doctorRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Doctor with id " + id + " does not exist"));

        // Update name:
        if(new_doctorName != null && new_doctorName.length() > 0 &&
                !Objects.equals(doctorModel.getDoctorName(), new_doctorName)){
            doctorModel.setDoctorName(new_doctorName);
        }

        // Update AddressPostalCode:
        if(new_doctorAddressPostalCode != null && new_doctorAddressPostalCode.length() > 0 &&
                !Objects.equals(doctorModel.getDoctorAddressPostalCode(), new_doctorAddressPostalCode)){
            doctorModel.setDoctorAddressPostalCode(new_doctorAddressPostalCode);
        }

        // Update AddressStreet:
        if(new_doctorAddressStreet != null && new_doctorAddressStreet.length() > 0 &&
                !Objects.equals(doctorModel.getDoctorAddressStreet(), new_doctorAddressStreet)){
            doctorModel.setDoctorAddressStreet(new_doctorAddressStreet);
        }

        // Update MobileNumber:
        if(new_doctorMobileNumber != null && new_doctorMobileNumber.length() > 0 &&
                !Objects.equals(doctorModel.getDoctorMobileNumber(), new_doctorMobileNumber)){
            doctorModel.setDoctorMobileNumber(new_doctorMobileNumber);
        }
    }

    @Override
    public List<DoctorModel> isPending()
    {
        Optional<List<DoctorModel>> pendingDoctorList;
        pendingDoctorList = doctorRepository.findPendingDoctors();
        return pendingDoctorList.orElse(null);
    }

    @Override
    public List<DoctorModel> isApproved()
    {
        Optional<List<DoctorModel>> approvedDoctorList;
        approvedDoctorList = doctorRepository.findApprovedDoctors();
        return approvedDoctorList.orElse(null);
    }

    @Override
    public List<DoctorModel> isBlocked() {
        Optional<List<DoctorModel>> blockedDoctorList;
        blockedDoctorList = doctorRepository.findBlockedDoctors();
        return blockedDoctorList.orElse(null);
    }

    @Override
    public void verifyDoctor(String email, boolean isActive, boolean isBlocked) {
        Optional<DoctorModel> doctorOptional = doctorRepository.findByDoctorEmail(email);

        if (doctorOptional.isPresent()) {
            DoctorModel doctor = doctorOptional.get();
            doctor.setActive(isActive);
            doctor.setBlocked(!isBlocked);
            doctorRepository.save(doctor);
        }
    }

    @Override
    public void blockDoctor(String email, boolean isBlocked) {
        Optional<DoctorModel> doctorOptional = doctorRepository.findByDoctorEmail(email);

        if (doctorOptional.isPresent()) {
            DoctorModel doctor = doctorOptional.get();
            doctor.setBlocked(isBlocked);
            doctorRepository.save(doctor);
        }
    }
}
