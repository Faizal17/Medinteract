package com.csci5308.medinteract.city.service;

import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.city.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }
    @Override
    public List<CityModel> fetchAll() {
        return cityRepository.findAll();
    }

    public Long getCityId(CityModel cityModel)
    {
        String city = cityModel.getCity();
        List<CityModel> cityModelList = cityRepository.findByCity(city);
        return cityModelList.get(0).getId();
    }
}


