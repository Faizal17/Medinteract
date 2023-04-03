package com.csci5308.medinteract.province.controller;

import static org.mockito.Mockito.when;

import com.csci5308.medinteract.province.model.ProvinceModel;
import com.csci5308.medinteract.province.service.ProvinceService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ProvinceController.class})
@ExtendWith(SpringExtension.class)
class ProvinceControllerTest {
    @Autowired
    private ProvinceController provinceController;

    @MockBean
    private ProvinceService provinceService;
    @Test
    void testFetchAll() throws Exception {

        // Arrange
        ProvinceModel provinceModel = new ProvinceModel();
        provinceModel.setAbbrv("NS");
        provinceModel.setCities(new ArrayList<>());
        provinceModel.setId(1L);
        provinceModel.setIsActive(true);
        provinceModel.setName("Nova Scotia");

        // Act
        ArrayList<ProvinceModel> provinceModelList = new ArrayList<>();
        provinceModelList.add(provinceModel);
        when(provinceService.fetchAll()).thenReturn(provinceModelList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/province/fetchAll");
        MockMvcBuilders.standaloneSetup(provinceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"msg\":\"All provinces fetched successfully\",\"isError\":false,\"data\":[{\"id\":1,\"abbrv\":\"NS\",\"name\":\"Nova Scotia\","
                                        + "\"cities\":[],\"isActive\":true}]}"));
    }

    @Test
    void testGetProvinceId() throws Exception {
        when(provinceService.getProvinceId(Mockito.<ProvinceModel>any())).thenReturn(1L);

        ProvinceModel provinceModel = new ProvinceModel();
        provinceModel.setAbbrv("NS");
        provinceModel.setCities(new ArrayList<>());
        provinceModel.setId(1L);
        provinceModel.setIsActive(true);
        provinceModel.setName("Nova Scotia");
        String content = (new ObjectMapper()).writeValueAsString(provinceModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/province/province_id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(provinceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"msg\":\"Province Id found\",\"isError\":false,\"data\":1}"));
    }
}

