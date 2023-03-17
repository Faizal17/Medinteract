package com.csci5308.medinteract.prescription.repository;
import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionModel,Long> {
    PrescriptionModel getByPrescriptionId(Long prescriptionId);
}
