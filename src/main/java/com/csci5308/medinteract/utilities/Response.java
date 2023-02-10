package com.csci5308.medinteract.utilities;



import java.util.HashMap;
import java.util.Map;

public class Response {
    Map<String, Object> json = new HashMap<>();

    public Response(Object body, boolean isError, String message){
        this.json.put("data", body);
        this.json.put("isError", isError);
        this.json.put("msg", message);
    }
    public Response(Object body, boolean isError, String message,int count){
        this.json.put("data", body);
        this.json.put("isError", isError);
        this.json.put("msg", message);
        this.json.put("count", count);
    }
    public Map<String, Object> getResponse(){
        System.out.println(json);
        return json;
    }
}
