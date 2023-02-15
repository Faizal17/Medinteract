package com.csci5308.medinteract.admin.Service;

import com.csci5308.medinteract.admin.Model.AdminModel;
import com.csci5308.medinteract.admin.Repository.AdminRepository;
import com.csci5308.medinteract.admin.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }



    @Override
    public boolean isAdminValid(String adminEmail, String adminPassword) {
        AdminModel admin = adminRepository.getReferenceById(adminEmail);
        return admin.getAdminPassword().equals(adminPassword);
    }
}
