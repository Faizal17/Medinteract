package com.csci5308.medinteract.feedback.Repository;

import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.feedback.Model.FeedbackModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackModel, Long> {

    List<FeedbackModel> findByDoctorId(Long doctorId);

    @Query("select f,p from FeedbackModel f JOIN PatientModel p ON f.patientId = p.id where f.doctorId = ?1 ORDER BY f.feedbackDate DESC")
    List<Object> findByDoctorIdAndPatient(Long doctorID);

    @Query("SELECT f.doctorId, AVG(f.rating) FROM FeedbackModel f GROUP by f.doctorId")
    List<Object> findAvgRatingOfDoctor();


    FeedbackModel findByDoctorIdAndPatientId(Long doctorId, Long patientId);
}
