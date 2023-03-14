package com.csci5308.medinteract.prescription.repository;

import com.csci5308.medinteract.patient.model.PatientModel;
import com.csci5308.medinteract.prescription.model.MedicineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<MedicineModel,Long> {

    @Query("select p.id from PatientModel p join PrescriptionModel r on p.id = r.patientId")
    List<PatientModel> findByPatientId(Long id);
}
