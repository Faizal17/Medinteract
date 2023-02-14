package com.csci5308.medinteract.city.repository;

import com.csci5308.medinteract.city.model.CityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityModel, Long> {
}
