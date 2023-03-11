package com.csci5308.medinteract.feedback.Controller;

import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.city.service.CityService;
import com.csci5308.medinteract.feedback.Model.FeedbackModel;
import com.csci5308.medinteract.feedback.Service.FeedbackService;
import com.csci5308.medinteract.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackServiceImpl;

    @Autowired
    public FeedbackController(FeedbackService feedbackServiceImpl){
        this.feedbackServiceImpl = feedbackServiceImpl;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity fetchAll()
    {
        List<FeedbackModel> feedbackModelList= feedbackServiceImpl.fetchAll();
        Response res = new Response(feedbackModelList, false, "All feedback fetched successfully");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/saveFeedback")
    public ResponseEntity saveFeedback(@RequestBody FeedbackModel feedbackModel)
    {
        feedbackServiceImpl.saveFeedback(feedbackModel);
        Response  res = new Response(feedbackModel, false, "Feedback added Successfully!");
        return new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
    }

    @PutMapping("/deleteAll")
    public void deleteAll()
    {
        feedbackServiceImpl.deleteAll();
    }
}
