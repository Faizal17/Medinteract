package com.csci5308.medinteract.city.controller;
import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.city.service.CityService;
import com.csci5308.medinteract.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {
    private final CityService cityServiceImpl;

    @Autowired
    public CityController(CityService cityServiceImpl){
        this.cityServiceImpl = cityServiceImpl;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity fetchAll()
    {
        List<CityModel> cityModelList= cityServiceImpl.fetchAll();
        Response res = new Response(cityModelList, false, "All citys fetched successfully");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }
}
