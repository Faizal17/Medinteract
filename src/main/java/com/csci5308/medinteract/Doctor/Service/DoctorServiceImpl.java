package com.csci5308.medinteract.Doctor.Service;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import com.csci5308.medinteract.Doctor.Repository.DoctorRepository;
import com.csci5308.medinteract.utilities.PasswordEncodeDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        if (newDoctor.isEmpty()) {
            return res;
        }
        res.put("id", newDoctor.get().getId());
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

}
