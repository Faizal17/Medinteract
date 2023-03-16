package com.csci5308.medinteract.prescription.controller;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.prescription.model.MedicineModel;
import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import com.csci5308.medinteract.prescription.service.PrescriptionServiceImpl;
import com.csci5308.medinteract.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    private final PrescriptionServiceImpl prescriptionServiceImpl;

    @Autowired
    public PrescriptionController(PrescriptionServiceImpl prescriptionServiceImpl) {
        this.prescriptionServiceImpl = prescriptionServiceImpl;
    }

    @PostMapping("/addMedicines")
    public ResponseEntity addMedicines(@RequestBody MedicineModel medicineModel) throws Exception {
            prescriptionServiceImpl.saveMedicine(medicineModel);
            Response  res = new Response(medicineModel, false, "Medicines added Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @PostMapping("/addPrescription")
    public ResponseEntity addMedicines(@RequestBody PrescriptionModel prescriptionModel) throws Exception {
            prescriptionServiceImpl.savePrescription(prescriptionModel);
            Response  res = new Response(prescriptionModel, false, "Prescription added Successfully!");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }
}
