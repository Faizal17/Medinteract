package com.csci5308.medinteract.prescription.repository;

import com.csci5308.medinteract.prescription.model.MedicineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineModel,Long> {
}
