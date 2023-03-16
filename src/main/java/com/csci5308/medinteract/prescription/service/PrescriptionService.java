package com.csci5308.medinteract.prescription.service;

import com.csci5308.medinteract.prescription.model.MedicineModel;
import com.csci5308.medinteract.prescription.model.PrescriptionModel;

public interface PrescriptionService {
    public MedicineModel saveMedicine(MedicineModel medicineModel);
    public PrescriptionModel savePrescription(PrescriptionModel prescriptionModel);
}
