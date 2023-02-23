package com.csci5308.medinteract.city.repository;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import com.csci5308.medinteract.city.model.CityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<CityModel, Long> {
    //@Query("SELECT c FROM CityModel c WHERE c.city= ':city'")
    List<CityModel> findByCity(String city);
}
