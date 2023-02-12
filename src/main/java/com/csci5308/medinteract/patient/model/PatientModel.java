package com.csci5308.medinteract.patient.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="patient")
public class PatientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank
    @NotNull
    @Email
    @Column(unique=true)
    private String patientEmail;

    @NotBlank
    @NotNull
    private String patientName;
    @NotBlank
    @NotNull
    private String patientAddressProvince;
    private String patientAddressPostalCode;
    @NotBlank
    @NotNull
    private String patientAddressStreet;

    @NotBlank
    @NotNull
    private char patientGender;

    @DateTimeFormat
    @NotBlank
    @NotNull
    private Date patientDOB;

    @Digits(integer = 2, fraction = 0)
    @NotBlank
    @NotNull
    private int patientAge;

    @Digits(integer = 10, fraction = 0)
    @NotBlank
    @NotNull
    private String patientMobileNumber;

    private boolean isActive;


    @NotBlank
    @NotNull
    private String patientPassword;






    public PatientModel() {
    }

    public PatientModel(String patientEmail, String patientPassword) {
        this.patientEmail = patientEmail;
        this.patientPassword = patientPassword;
    }

    public PatientModel(Long id, String patientEmail, String patientPassword) {
        this.id = id;
        this.patientEmail = patientEmail;
        this.patientPassword = patientPassword;
    }


    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAddressProvince() {
        return patientAddressProvince;
    }

    public void setPatientAddressProvince(String patientAddressProvince) {
        this.patientAddressProvince = patientAddressProvince;
    }

    public String getPatientAddressPostalCode() {
        return patientAddressPostalCode;
    }

    public void setPatientAddressPostalCode(String patientAddressPostalCode) {
        this.patientAddressPostalCode = patientAddressPostalCode;
    }

    public String getPatientAddressStreet() {
        return patientAddressStreet;
    }

    public void setPatientAddressStreet(String patientAddressStreet) {
        this.patientAddressStreet = patientAddressStreet;
    }

    public char getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(char patientGender) {
        this.patientGender = patientGender;
    }

    public Date getPatientDOB() {
        return patientDOB;
    }

    public void setPatientDOB(Date patientDOB) {
        this.patientDOB = patientDOB;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientMobileNumber() {
        return patientMobileNumber;
    }

    public void setPatientMobileNumber(String patientMobileNumber) {
        this.patientMobileNumber = patientMobileNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientPassword() {
        return patientPassword;
    }

    public void setPatientPassword(String patientPassword) {
        this.patientPassword = patientPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Patient patient = (Patient) o;
//        return Objects.equals(id, patient.id) && Objects.equals(patientEmail, patient.patientEmail) && Objects.equals(patientPassword, patient.patientPassword);
//    }
//
//    @Override
//    public String toString() {
//        return "Patient{" +
//                "id=" + id +
//                ", patientEmail='" + patientEmail + '\'' +
//                '}';
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, patientEmail, patientPassword);
//
//    }
}
