package com.csci5308.medinteract.province.repository;

import com.csci5308.medinteract.province.model.ProvinceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<ProvinceModel, Long> {
}
