package com.csci5308.medinteract.appointment.repository;

import com.csci5308.medinteract.appointment.model.AppointmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentModel, Long> {
    @Query("SELECT a FROM AppointmentModel a WHERE a.patientId = ?1 AND a.isActive = true")
    List<AppointmentModel> findByPatientId(Long patientId);

    @Query("SELECT a FROM AppointmentModel a WHERE a.doctorId = ?1 AND a.isActive = true")
    List<AppointmentModel> findByDoctorId(Long doctorId);
}
