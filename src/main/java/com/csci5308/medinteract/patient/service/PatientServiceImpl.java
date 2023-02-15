package com.csci5308.medinteract.patient.service;

import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.repository.PatientRepository;
import com.csci5308.medinteract.utilities.PasswordEncodeDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService{
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    @Override
    public List<PatientModel> fetchAll() {
        return patientRepository.findAll();
    }

    @Override
    public PatientModel savePatient(PatientModel patientModel) {
        return patientRepository.save(patientModel);
    }

    @Override
    public Optional<PatientModel> findById(Long id) {
      return  patientRepository.findById(id);
    }



    @Override
    public Map<String, Object> checkIfEmailExists(String email) {
        Map<String, Object> res = new HashMap<>();
        Optional<PatientModel> newPatient = patientRepository.findByPatientEmail(email);
        boolean result = newPatient.isPresent() && (newPatient.get().isActive() || newPatient.get().isBlocked());
        res.put("result", result);
        if(newPatient.isPresent()){
            res.put("id", newPatient.get().getId());
        }
        return res;
    }

    @Override
    public PatientModel getPatientByEmail(String email) {
        Optional<PatientModel> newPatient = patientRepository.findByPatientEmail(email);
        return newPatient.orElse(null);
    }

    @Override
    public boolean isPatientValid(String patientEmail, String patientPassword) throws Exception {
        Optional<PatientModel> patient = patientRepository.findByPatientEmail(patientEmail);

        String encodedPassword = encodePassword(patientPassword);
        if(patient.isPresent() && patient.get().getPatientPassword().equals(encodedPassword) && patient.get().isActive() && !patient.get().isBlocked())
        {
           //valid patient
                    System.out.println(patient.get().getPatientEmail());
                    System.out.println(patient.get().getPatientPassword());
            return true;
        }

        return false;
    }

    public String encodePassword(String password) throws Exception {
        System.out.println("password to encode : "+password);
        return PasswordEncodeDecode.encrypt(password);
    }

    @Override
    public String decodePassword(String password) throws Exception {
        return PasswordEncodeDecode.decrypt(password);
    }


}
