package com.csci5308.medinteract.feedback.Service;

import com.csci5308.medinteract.feedback.Model.FeedbackModel;

import java.util.List;

public interface FeedbackService {

    public List<FeedbackModel> fetchAll();
    public void saveFeedback(FeedbackModel feedbackModel);

    public void deleteAll();
}
