package com.csci5308.medinteract.patient.repository;

import com.csci5308.medinteract.patient.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, Long> {

//    @Query("SELECT p FROM PatientModel p WHERE p.patientEmail=?1")
//    @Query(value = "SELECT * FROM patient p WHERE p.patient_email=?1",nativeQuery = true)
    Optional<PatientModel> findByPatientEmail(String patientEmail);


}
