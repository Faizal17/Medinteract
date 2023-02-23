package com.csci5308.medinteract.Doctor.Repository;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorModel,Long> {

    Optional<DoctorModel> findByDoctorEmail(String doctorEmail);

    //@Query("SELECT d FROM DoctorModel d WHERE d.doctorAddressCity= :city")
    //List<DoctorModel> fetchDoctorsOnCity(Long city);
    List<DoctorModel> findByDoctorAddressCity(Long cityId);

}
