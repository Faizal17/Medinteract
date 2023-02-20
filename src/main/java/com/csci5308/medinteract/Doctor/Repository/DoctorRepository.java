package com.csci5308.medinteract.Doctor.Repository;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorModel,Long> {
    Optional<DoctorModel> findByDoctorEmail(String doctorEmail);

    @Query("select d from DoctorModel d where d.isActive = false and d.isBlocked = false")
    Optional<List<DoctorModel>> findPendingDoctors();

    @Query("select d from DoctorModel d where d.isActive = true and d.isBlocked = false")
    Optional<List<DoctorModel>> findApprovedDoctors();

    @Query("select d from DoctorModel d where d.isBlocked = true")
    Optional<List<DoctorModel>> findBlockedDoctors();

}
