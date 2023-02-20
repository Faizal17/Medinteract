package com.csci5308.medinteract.Doctor.Service;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DoctorService {
    List<DoctorModel> fetchAll();
    DoctorModel saveDoctor(DoctorModel DoctorModel);
    Optional<DoctorModel> findById(Long id);

    Map<String, Object> checkIfEmailExists(String email);

    DoctorModel getDoctorByEmail(String email);

    boolean isDoctorValid(String DoctorEmail, String DoctorPassword) throws Exception;
    String encodePassword(String password) throws Exception;

    List<DoctorModel> isPending();
}
