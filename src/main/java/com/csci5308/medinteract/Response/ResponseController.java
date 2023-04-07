package com.csci5308.medinteract.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server")
public class ResponseController {
    @GetMapping("/healthCheck")
    public ResponseEntity healthCheck() {
        Response res = new Response("", false, "Server is running on port 6969");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }
}
