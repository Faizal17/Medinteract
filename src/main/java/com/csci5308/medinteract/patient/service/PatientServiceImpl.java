package com.csci5308.medinteract.patient.service;

import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService{
    private final PatientRepository patientRepository;

//    @Autowired
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
    public boolean checkEmail(PatientModel patientModel) {
        boolean result = false;
        Optional<PatientModel> newPatient = patientRepository.findByPatientEmail(patientModel.getPatientEmail());
        if(newPatient.isPresent())
        {
            result = true;
        }
        else
        {
            result = false;
        }
        return result;
    }


//    @Override
//    public boolean existsByEmailAndPassword(String patientEmail, String patientPassword) {
//        return patientRepository.existsByEmailAndPassword(patientEmail,patientPassword);
//    }




}
