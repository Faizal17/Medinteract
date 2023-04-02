package com.csci5308.medinteract.doctor.Service;

import com.csci5308.medinteract.doctor.Model.DoctorModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DoctorService {
    List<DoctorModel> fetchAll();

    DoctorModel saveDoctor(DoctorModel DoctorModel);

    Optional<DoctorModel> findById(Long id);

    Map<String, Object> checkIfEmailExists(String email);

    DoctorModel getDoctorByEmail(String email);

    boolean isDoctorValid(String DoctorEmail, String DoctorPassword) throws Exception;

    String encodePassword(String password) throws Exception;

    List<DoctorModel> fetchDoctorsOnCity(DoctorModel doctorModel);

    List<DoctorModel> fetchDoctorsOnProvince(DoctorModel doctorModel);

    List<DoctorModel> fetchDoctorsOnName(DoctorModel doctorModel);

    List<DoctorModel> fetchDoctorsOnQualification(DoctorModel doctorModel);

    List<Map<String, Object>> findDoctorOnDetailsWithCity(DoctorModel doctorModel);

    List<Map<String, Object>> findDoctorOnDetailsWithCityAndFeedback(DoctorModel doctorModel);

    List<DoctorModel> getAllDoctors();

    Optional<DoctorModel> getDoctorById(Long id);

    void deleteDoctorById(Long id);

    void updateDoctorById(Long id, String newDoctorName, String newDoctorAddressPostalCode,
            String newDoctorAddressStreet, String newDoctorMobileNumber);

    List<DoctorModel> isPending();

    List<DoctorModel> isApproved();

    List<DoctorModel> isBlocked();

    void verifyDoctor(String email, boolean isActive, boolean isBlocked);

    void blockDoctor(String email, boolean isBlocked);

    Optional<List<DoctorModel>> fetchDoctor(Long id);

    List<DoctorModel> getDoctorByDetails(DoctorModel doctorModel);
}