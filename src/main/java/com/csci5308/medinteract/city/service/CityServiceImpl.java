package com.csci5308.medinteract.city.service;

import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.city.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
