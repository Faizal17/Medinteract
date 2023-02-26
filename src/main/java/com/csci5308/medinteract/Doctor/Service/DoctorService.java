package com.csci5308.medinteract.Doctor.Service;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import com.csci5308.medinteract.patient.model.PatientModel;

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
    
    List<DoctorModel> getAllDoctors();

    Optional<DoctorModel> getDoctorById(Long id);

    void deleteDoctorById(Long id);

    void updateDoctorById(Long id, String newDoctorName, String newDoctorAddressPostalCode, String newDoctorAddressStreet, String newDoctorMobileNumber);
}
