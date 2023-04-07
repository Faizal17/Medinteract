package com.csci5308.medinteract.admin.service;

import com.csci5308.medinteract.admin.model.AdminModel;
import com.csci5308.medinteract.admin.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AdminServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AdminServiceImplTest {
    @MockBean
    private AdminRepository adminRepository;

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Test
    void testIsAdminValid() {

        // Arrange
        AdminModel adminModel = new AdminModel();

        // Act
        adminModel.setActive(true);
        adminModel.setAdminEmail("memo@dal.ca");
        adminModel.setAdminPassword("12345");
        when(adminRepository.getReferenceById(Mockito.<String>any())).thenReturn(adminModel);

        // Assert
        assertTrue(adminServiceImpl.isAdminValid("memo@dal.ca", "12345"));
        verify(adminRepository).getReferenceById(Mockito.<String>any());
    }
}

