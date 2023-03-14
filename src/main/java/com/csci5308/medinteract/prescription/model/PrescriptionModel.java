package com.csci5308.medinteract.prescription.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescription")
public class PrescriptionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "prescription_id", nullable = false)
    private Long prescriptionId;

    @NotBlank
    @NotNull
    private Long patientId;

    @NotBlank
    @NotNull
    private Long doctorId;

    private LocalDateTime prescriptionTime;

    public Long getId() {
        return prescriptionId;
    }

    public void setId(Long id) {
        this.prescriptionId = id;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getPrescriptionTime() {
        return prescriptionTime;
    }

    public void setPrescriptionTime(LocalDateTime prescriptionTime) {
        this.prescriptionTime = prescriptionTime;
    }
}
