package com.csci5308.medinteract.prescription.service;

import com.csci5308.medinteract.prescription.model.PrescriptionModel;

public interface PrescriptionService {

    PrescriptionModel savePrescription(PrescriptionModel prescriptionModel);
    PrescriptionModel findPrescriptionById(Long prescriptionId);
    Iterable<PrescriptionModel> findAllPrescription();
}
