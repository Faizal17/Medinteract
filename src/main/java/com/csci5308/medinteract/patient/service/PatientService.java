package com.csci5308.medinteract.patient.service;

import com.csci5308.medinteract.patient.model.PatientModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PatientService {
    List<PatientModel> fetchAll();

    PatientModel savePatient(PatientModel patientModel);

    Optional<PatientModel> findById(Long id);

    Map<String, Object> checkIfEmailExists(String email);

    PatientModel getPatientByEmail(String email);

    boolean isPatientValid(String patientEmail, String patientPassword) throws Exception;

    String encodePassword(String password) throws Exception;

    String decodePassword(String password) throws Exception;


    List<PatientModel> getPatients();

    void deletePatientById(Long id);

    void updatePatientById(Long id, String newPatientName, String newPatientAddressPostalCode, String newPatientAddressStreet, String newPatientMobileNumber);

    Optional<PatientModel> getPatientById(Long id);
}

