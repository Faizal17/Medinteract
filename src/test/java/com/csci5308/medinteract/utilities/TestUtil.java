package com.csci5308.medinteract.utilities;

import com.jayway.jsonpath.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUtil {
   public static MvcResult getResultFromPostAPI(String apiURL, String json, MockMvc mockMvc) throws UnsupportedEncodingException, Exception {
      MvcResult mvcResult =   mockMvc.perform(post(apiURL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()).andReturn();
      return mvcResult;
    }

    public static MvcResult getResultFromGetAPI(String apiURL,MockMvc mockMvc) throws UnsupportedEncodingException, Exception {
        MvcResult mvcResult = mockMvc.perform(get(apiURL))
                .andExpect(status().isOk())
                .andReturn();
        return mvcResult;
    }
   public static boolean getErrorStatusFromMvcResult(MvcResult mvcResult) throws UnsupportedEncodingException {
        return JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.isError");

    }
}
