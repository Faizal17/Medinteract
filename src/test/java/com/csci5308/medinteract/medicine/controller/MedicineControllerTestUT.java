package com.csci5308.medinteract.medicine.controller;

import com.csci5308.medinteract.medicine.repository.MedicineRepository;
import com.csci5308.medinteract.medicine.service.MedicineService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = MedicineController.class)
public class MedicineControllerTestUT {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MedicineRepository medicineRepository;

    @MockBean
    private MedicineService medicineService;


}
