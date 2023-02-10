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
    public boolean checkIfEmailExists(PatientModel patientModel) {
        boolean result ;
        Optional<PatientModel> newPatient = patientRepository.findByPatientEmail(patientModel.getPatientEmail());
        result = newPatient.isPresent();
        return result;
    }






}
