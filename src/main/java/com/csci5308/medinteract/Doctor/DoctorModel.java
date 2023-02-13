package com.csci5308.medinteract.Doctor;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="doctor")
public class DoctorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank
    @NotNull
    @Email
    @Column(unique=true)
    private String doctorEmail;

    @NotBlank
    @NotNull
    private String doctorName;
    @NotBlank
    @NotNull
    private String doctorAddressProvince;
    private String doctorAddressPostalCode;
    @NotBlank
    @NotNull
    private String doctorAddressStreet;

    @NotBlank
    @NotNull
    private char doctorGender;

    @DateTimeFormat
    @NotBlank
    @NotNull
    private Date doctorDOB;

    @Digits(integer = 2, fraction = 0)
    @NotBlank
    @NotNull
    private int doctorAge;

    @Digits(integer = 10, fraction = 0)
    @NotBlank
    @NotNull
    private String doctorMobileNumber;

    private boolean isActive;

    private String doctorQualification;
    @NotBlank
    @NotNull
    private String doctorPassword;

    private String emailToken;


    public DoctorModel() {
    }

    public DoctorModel(Long id, String doctorEmail, String doctorName, String doctorAddressProvince, String doctorAddressPostalCode, String doctorAddressStreet, char doctorGender, Date doctorDOB, int doctorAge, String doctorMobileNumber, boolean isActive, String doctorQualification, String doctorPassword, String emailToken) {
        this.id = id;
        this.doctorEmail = doctorEmail;
        this.doctorName = doctorName;
        this.doctorAddressProvince = doctorAddressProvince;
        this.doctorAddressPostalCode = doctorAddressPostalCode;
        this.doctorAddressStreet = doctorAddressStreet;
        this.doctorGender = doctorGender;
        this.doctorDOB = doctorDOB;
        this.doctorAge = doctorAge;
        this.doctorMobileNumber = doctorMobileNumber;
        this.isActive = true;
        this.doctorQualification = doctorQualification;
        this.doctorPassword = doctorPassword;
        this.emailToken = emailToken;
    }

    public DoctorModel(String doctorEmail, String doctorPassword) {
        this.doctorEmail = doctorEmail;
        this.doctorPassword = doctorPassword;
    }

    public DoctorModel(String doctorEmail, String doctorName, String doctorAddressProvince, String doctorAddressPostalCode, String doctorAddressStreet, char doctorGender, Date doctorDOB, int doctorAge, String doctorMobileNumber, boolean isActive, String doctorQualification, String doctorPassword, String emailToken) {
        this.doctorEmail = doctorEmail;
        this.doctorName = doctorName;
        this.doctorAddressProvince = doctorAddressProvince;
        this.doctorAddressPostalCode = doctorAddressPostalCode;
        this.doctorAddressStreet = doctorAddressStreet;
        this.doctorGender = doctorGender;
        this.doctorDOB = doctorDOB;
        this.doctorAge = doctorAge;
        this.doctorMobileNumber = doctorMobileNumber;
        this.isActive = isActive;
        this.doctorQualification = doctorQualification;
        this.doctorPassword = doctorPassword;
        this.emailToken = emailToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorAddressProvince() {
        return doctorAddressProvince;
    }

    public void setDoctorAddressProvince(String doctorAddressProvince) {
        this.doctorAddressProvince = doctorAddressProvince;
    }

    public String getDoctorAddressPostalCode() {
        return doctorAddressPostalCode;
    }

    public void setDoctorAddressPostalCode(String doctorAddressPostalCode) {
        this.doctorAddressPostalCode = doctorAddressPostalCode;
    }

    public String getDoctorAddressStreet() {
        return doctorAddressStreet;
    }

    public void setDoctorAddressStreet(String doctorAddressStreet) {
        this.doctorAddressStreet = doctorAddressStreet;
    }

    public char getDoctorGender() {
        return doctorGender;
    }

    public void setDoctorGender(char doctorGender) {
        this.doctorGender = doctorGender;
    }

    public Date getDoctorDOB() {
        return doctorDOB;
    }

    public void setDoctorDOB(Date doctorDOB) {
        this.doctorDOB = doctorDOB;
    }

    public int getDoctorAge() {
        return doctorAge;
    }

    public void setDoctorAge(int doctorAge) {
        this.doctorAge = doctorAge;
    }

    public String getDoctorMobileNumber() {
        return doctorMobileNumber;
    }

    public void setDoctorMobileNumber(String doctorMobileNumber) {
        this.doctorMobileNumber = doctorMobileNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDoctorQualification() {
        return doctorQualification;
    }

    public void setDoctorQualification(String doctorQualification) {
        this.doctorQualification = doctorQualification;
    }

    public String getDoctorPassword() {
        return doctorPassword;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }
}
