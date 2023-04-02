package com.csci5308.medinteract.prescription.controller;

import com.csci5308.medinteract.medicine.model.MedicineModel;
import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import com.csci5308.medinteract.prescription.repository.PrescriptionRepository;
import com.csci5308.medinteract.prescription.service.PrescriptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PrescriptionController.class)
public class PrescriptionControllerTestUT {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @MockBean
    private PrescriptionService prescriptionService;

    @Test
    public void testFetchAllPrescriptions() throws Exception {
        PrescriptionModel prescription1 = new PrescriptionModel();
        prescription1.setId(1L);
        prescription1.setPatientId(1L);
        prescription1.setDoctorId(1L);
        prescription1.setPrescriptionTime(LocalDateTime.now());
        prescription1.setMedicines(Collections.singletonList(new MedicineModel()));

        PrescriptionModel prescription2 = new PrescriptionModel();
        prescription2.setId(2L);
        prescription2.setPatientId(2L);
        prescription2.setDoctorId(2L);
        prescription2.setPrescriptionTime(LocalDateTime.now());
        prescription2.setMedicines(Collections.singletonList(new MedicineModel()));

        List<PrescriptionModel> prescriptionList = Arrays.asList(prescription1, prescription2);

        Mockito.when(prescriptionService.findAllPrescription()).thenReturn(prescriptionList);

        mockMvc.perform(get("/prescription/fetchAll"))
                .andExpect(status().isOk());
    }
}
