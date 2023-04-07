package com.csci5308.medinteract.medicine.model;

import com.csci5308.medinteract.prescription.model.PrescriptionModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "pres_id")
//    private PrescriptionModel pres;

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
//    @JsonBackReference
//    public PrescriptionModel getPres() {
//        return pres;
//    }
//
//    public void setPres(PrescriptionModel pres) {
//        this.pres = pres;
//    }

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

    public boolean isMorning() {
        return isMorning;
    }

//    public void setMorning(boolean morning) {
//        isMorning = morning;
//    }

    public boolean isAfternoon() {
        return isAfternoon;
    }

//    public void setAfternoon(boolean afternoon) {
//        isAfternoon = afternoon;
//    }

    public boolean isEvening() {
        return isEvening;
    }

//    public void setEvening(boolean evening) {
//        isEvening = evening;
//    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

//    public void setAdditionalNotes(String additionalNotes) {
//        this.additionalNotes = additionalNotes;
//    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long prescriptionId) {
        this.medicineId = prescriptionId;
    }

    public MedicineModel(Long medicineId, String medicineName, int medicineAmount, boolean isMorning, boolean isAfternoon, boolean isEvening, String additionalNotes) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicineAmount = medicineAmount;
        this.isMorning = isMorning;
        this.isAfternoon = isAfternoon;
        this.isEvening = isEvening;
        this.additionalNotes = additionalNotes;
    }
}
