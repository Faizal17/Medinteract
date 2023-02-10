package com.csci5308.medinteract.patient.service;

import com.csci5308.medinteract.patient.model.PatientModel;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<PatientModel> fetchAll();
    PatientModel savePatient(PatientModel patientModel);
    Optional<PatientModel> findById(Long id);

//    boolean existsByEmailAndPassword(String patientEmail, String patientPassword);
    boolean checkEmail(PatientModel patientModel);


}
