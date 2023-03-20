package com.csci5308.medinteract.patient.service;

import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.repository.PatientRepository;
import com.csci5308.medinteract.utilities.PasswordEncodeDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
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
        return patientRepository.findById(id);
    }


    @Override
    public Map<String, Object> checkIfEmailExists(String email) {
        Map<String, Object> res = new HashMap<>();
        Optional<PatientModel> newPatient = patientRepository.findByPatientEmail(email);
        boolean result = newPatient.isPresent() && (newPatient.get().isActive() || newPatient.get().isBlocked());
        res.put("result", result);
        if (newPatient.isPresent()) {
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
        if (patient.isPresent() && patient.get().getPatientPassword().equals(encodedPassword) && patient.get().isActive() && !patient.get().isBlocked()) {
            //valid patient
            System.out.println(patient.get().getPatientEmail());
            System.out.println(patient.get().getPatientPassword());
            return true;
        }
        return false;
    }

    public String encodePassword(String password) throws Exception {
        System.out.println("password to encode : " + password);
        return PasswordEncodeDecode.encrypt(password);
    }

    @Override
    public String decodePassword(String password) throws Exception {
        return PasswordEncodeDecode.decrypt(password);
    }

    @Override
    public List<PatientModel> getPatients() {
        return patientRepository.findAll();
    }

    public Optional<PatientModel> getPatientById(Long id){
        return patientRepository.findById(id);
    }

    @Override
    public void deletePatientById(Long id){
        patientRepository.deleteById(id);
    }

//    @Override
    @Transactional
    public void updatePatientById(
            Long id,
            String new_patientName,
            String new_patientAddressPostalCode,
            String new_patientAddressStreet,
            String new_patientMobileNumber) {

        PatientModel patientModel = patientRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Patient with id " + id + " does not exist"));

        // Update name:
        if(new_patientName != null && new_patientName.length() > 0 &&
                !Objects.equals(patientModel.getPatientName(), new_patientName)){
            patientModel.setPatientName(new_patientName);
        }

        // Update AddressPostalCode:
        if(new_patientAddressPostalCode != null && new_patientAddressPostalCode.length() > 0 &&
                !Objects.equals(patientModel.getPatientAddressPostalCode(), new_patientAddressPostalCode)){
            patientModel.setPatientAddressPostalCode(new_patientAddressPostalCode);
        }

        // Update AddressStreet:
        if(new_patientAddressStreet != null && new_patientAddressStreet.length() > 0 &&
                !Objects.equals(patientModel.getPatientAddressStreet(), new_patientAddressStreet)){
            patientModel.setPatientAddressStreet(new_patientAddressStreet);
        }

        // Update MobileNumber:
        if(new_patientMobileNumber != null && new_patientMobileNumber.length() > 0 &&
                !Objects.equals(patientModel.getPatientMobileNumber(), new_patientMobileNumber)){
            patientModel.setPatientMobileNumber(new_patientMobileNumber);
        }
    }

    @Override
    public Optional<List<PatientModel>> fetchPatientsWithAppointment(Long id) {
        return patientRepository.findByPatientId(id);
    }
}
