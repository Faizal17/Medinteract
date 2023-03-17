package com.csci5308.medinteract.medicine.model;

import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

//    @NotBlank
//    @NotNull
//    private Long patientId;

    @ManyToOne
    @JoinColumn(name = "pres_id")
    private PrescriptionModel pres;

    public MedicineModel() {
    }

    @NotBlank
    @NotNull
    private String medicineName;

    @NotBlank
    @NotNull
    private int medicineAmount;

    private boolean isMorning;

    private boolean isAfternoon;

    private boolean isEvening;

    private String additionalNotes;

//    public Long getPatientId() {
//        return patientId;
//    }
//
//    public void setPatientId(Long patientId) {
//        this.patientId = patientId;
//    }
    @JsonBackReference
    public PrescriptionModel getPres() {
        return pres;
    }

    public void setPres(PrescriptionModel pres) {
        this.pres = pres;
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
        return isMorning;
    }

    public void setInMorning(boolean inMorning) {
        isMorning = inMorning;
    }

    public boolean isInAfternoon() {
        return isAfternoon;
    }

    public void setInAfternoon(boolean inAfternoon) {
        isAfternoon = inAfternoon;
    }

    public boolean isInEvening() {
        return isEvening;
    }

    public void setInEvening(boolean inEvening) {
        isEvening = inEvening;
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
