package com.csci5308.medinteract.province.controller;

import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.province.model.ProvinceModel;
import com.csci5308.medinteract.province.service.ProvinceService;
import com.csci5308.medinteract.utilities.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/province")
public class ProvinceController {
    private final ProvinceService provinceServiceImpl;

    @Autowired
    public ProvinceController(ProvinceService provinceServiceImpl){
        this.provinceServiceImpl = provinceServiceImpl;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity fetchAll()
    {
        List<ProvinceModel> provinceModelList= provinceServiceImpl.fetchAll();
        System.out.println(provinceModelList.get(0).getId());
        Response res = new Response(provinceModelList, false, "All provinces fetched successfully");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

}
