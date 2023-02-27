package com.csci5308.medinteract.Doctor.Repository;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorModel,Long> {
    Optional<DoctorModel> findByDoctorEmail(String doctorEmail);


    List<DoctorModel> findByDoctorAddressCity(Long cityId);
    List<DoctorModel> findByDoctorAddressProvince(Long provinceId);
    List<DoctorModel> findByDoctorNameContaining(String name);
    List<DoctorModel> findByDoctorQualification(String qualification);
    @Modifying
    @Query("Update DoctorModel SET isActive = false WHERE id = ?1")
    void deleteById(Long id);
}
