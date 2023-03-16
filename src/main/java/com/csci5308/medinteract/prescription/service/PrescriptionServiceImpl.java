package com.csci5308.medinteract.prescription.service;

import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.prescription.model.MedicineModel;
import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import com.csci5308.medinteract.prescription.repository.MedicineRepository;
import com.csci5308.medinteract.prescription.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    PrescriptionRepository prescriptionRepository;
    MedicineRepository medicineRepository;

    @Override
    public MedicineModel saveMedicine(MedicineModel medicineModel) {
        return medicineRepository.save(medicineModel);
    }

    @Override
    public PrescriptionModel savePrescription(PrescriptionModel prescriptionModel) {
        return prescriptionRepository.save(prescriptionModel);
    }
}
