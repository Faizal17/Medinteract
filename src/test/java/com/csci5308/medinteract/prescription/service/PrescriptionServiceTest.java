package com.csci5308.medinteract.prescription.service;

import com.csci5308.medinteract.medicine.repository.MedicineRepository;
import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import com.csci5308.medinteract.prescription.repository.PrescriptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PrescriptionServiceTest {

    @Autowired
    private PrescriptionService prescriptionService;

    @MockBean
    private PrescriptionRepository prescriptionRepository;

    @MockBean
    private MedicineRepository medicineRepository;

    @Test
    public void savePrescriptionTest() {
        PrescriptionModel prescriptionModel = new PrescriptionModel();
        prescriptionModel.setId(1L);
        when(prescriptionRepository.save(prescriptionModel)).thenReturn(prescriptionModel);
        PrescriptionModel savedPrescription = prescriptionService.savePrescription(prescriptionModel);
        assertEquals(prescriptionModel.getId(), savedPrescription.getId());
    }

    @Test
    public void fetchPrescriptionTest() {
        List<PrescriptionModel> prescriptionModels = new ArrayList<>();
        PrescriptionModel prescriptionModel1 = new PrescriptionModel();
        prescriptionModel1.setId(1L);
        PrescriptionModel prescriptionModel2 = new PrescriptionModel();
        prescriptionModel2.setId(2L);
        prescriptionModels.add(prescriptionModel1);
        prescriptionModels.add(prescriptionModel2);
        when(prescriptionRepository.getPrescriptionModelBy(1L)).thenReturn(Optional.of(prescriptionModels));
        Optional<List<PrescriptionModel>> fetchedPrescriptions = prescriptionService.fetchPrescription(1L);
        assertTrue(fetchedPrescriptions.isPresent());
        assertEquals(2, fetchedPrescriptions.get().size());
        assertEquals(prescriptionModel1.getId(), fetchedPrescriptions.get().get(0).getId());
        assertEquals(prescriptionModel2.getId(), fetchedPrescriptions.get().get(1).getId());
    }

    @Test
    public void findAllPrescriptionTest() {
        List<PrescriptionModel> prescriptionModels = new ArrayList<>();
        PrescriptionModel prescriptionModel1 = new PrescriptionModel();
        prescriptionModel1.setId(1L);
        PrescriptionModel prescriptionModel2 = new PrescriptionModel();
        prescriptionModel2.setId(2L);
        prescriptionModels.add(prescriptionModel1);
        prescriptionModels.add(prescriptionModel2);
        when(prescriptionRepository.findAll()).thenReturn(prescriptionModels);
        Iterable<PrescriptionModel> fetchedPrescriptions = prescriptionService.findAllPrescription();
        assertNotNull(fetchedPrescriptions);
        Iterator<PrescriptionModel> iterator = fetchedPrescriptions.iterator();
        assertEquals(prescriptionModel1.getId(), iterator.next().getId());
        assertEquals(prescriptionModel2.getId(), iterator.next().getId());
    }

}
