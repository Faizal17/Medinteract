package com.csci5308.medinteract.utilities.JWT;

import com.csci5308.medinteract.utilities.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
public class JWTController {

    private final JWT jwtTokenUtil;

    public JWTController(JWT jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @GetMapping("/validateJWTToken")
    public ResponseEntity validateJWTToken(@RequestBody String token)
    {
        if(!token.isEmpty() && jwtTokenUtil.validateToken(token))
        {
            Response res = new Response(jwtTokenUtil.extractClaims(token), false, "Token is Valid");
            return  new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        else
        {
            Response  res = new Response(jwtTokenUtil.extractClaims(token), true, "Token is InValid");
            return  new ResponseEntity<>(res.getResponse(),HttpStatus.OK);
        }
    }
}
