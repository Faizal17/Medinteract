package com.csci5308.medinteract.admin.Repository;

import com.csci5308.medinteract.admin.Model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminModel,String> {
    @Override
    Optional<AdminModel> findById(String s);
}
