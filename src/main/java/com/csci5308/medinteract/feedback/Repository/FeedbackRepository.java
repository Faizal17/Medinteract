package com.csci5308.medinteract.feedback.Repository;

import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.feedback.Model.FeedbackModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackModel, Long> {

}
