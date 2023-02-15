package com.csci5308.medinteract.province.service;

import com.csci5308.medinteract.province.model.ProvinceModel;
import com.csci5308.medinteract.province.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService{
    ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceServiceImpl(ProvinceRepository provinceRepository){
        this.provinceRepository = provinceRepository;
    }

    @Override
    public List<ProvinceModel> fetchAll() {
        return provinceRepository.findAll();
    }
}