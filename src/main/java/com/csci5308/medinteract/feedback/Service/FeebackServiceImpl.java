package com.csci5308.medinteract.feedback.Service;

import com.csci5308.medinteract.feedback.Model.FeedbackModel;
import com.csci5308.medinteract.feedback.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeebackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeebackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public List<FeedbackModel> fetchAll()
    {
        return feedbackRepository.findAll();
    }

    public void saveFeedback(FeedbackModel feedbackModel)
    {
        feedbackRepository.save(feedbackModel);
    }

    public void deleteAll()
    {
        feedbackRepository.deleteAll();
    }
}
