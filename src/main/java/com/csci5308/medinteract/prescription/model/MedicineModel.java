package com.csci5308.medinteract.prescription.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "medicine")
public class MedicineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "medicine_id", nullable = false)
    private Long medicineId;

    @NotBlank
    @NotNull
    private Long patientId;

    @NotBlank
    @NotNull
    private String medicineName;

    @NotBlank
    @NotNull
    private int medicineAmount;

    private boolean isInMorning;

    private boolean isInAfternoon;

    private boolean isInEvening;

    private String additionalNotes;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getMedicineAmount() {
        return medicineAmount;
    }

    public void setMedicineAmount(int medicineAmount) {
        this.medicineAmount = medicineAmount;
    }

    public boolean isInMorning() {
        return isInMorning;
    }

    public void setInMorning(boolean inMorning) {
        isInMorning = inMorning;
    }

    public boolean isInAfternoon() {
        return isInAfternoon;
    }

    public void setInAfternoon(boolean inAfternoon) {
        isInAfternoon = inAfternoon;
    }

    public boolean isInEvening() {
        return isInEvening;
    }

    public void setInEvening(boolean inEvening) {
        isInEvening = inEvening;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long prescriptionId) {
        this.medicineId = prescriptionId;
    }
}
