package com.csci5308.medinteract.admin.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AdminModelTest {
    @Test
    void testConstructorWithEmailPassword() {

        // Arrange
        AdminModel actualAdminModel = new AdminModel();

        // Act
        actualAdminModel.setActive(true);
        actualAdminModel.setAdminEmail("memo@dal.ca");
        actualAdminModel.setAdminPassword("12345");

        // Assert
        assertEquals("memo@dal.ca", actualAdminModel.getAdminEmail());
        assertEquals("12345", actualAdminModel.getAdminPassword());
        assertTrue(actualAdminModel.isActive());
    }

    @Test
    void testConstructorWithUsernameEmailPassword() {

        // Arrange
        AdminModel actualAdminModel = new AdminModel("MeMoRam", "12345");

        // Act
        actualAdminModel.setActive(true);
        actualAdminModel.setAdminEmail("memo@dal.ca");
        actualAdminModel.setAdminPassword("12345");

        // Assert
        assertEquals("memo@dal.ca", actualAdminModel.getAdminEmail());
        assertEquals("12345", actualAdminModel.getAdminPassword());
        assertTrue(actualAdminModel.isActive());
    }
}

