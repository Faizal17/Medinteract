package com.csci5308.medinteract.utilities.JWT;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWT {

    @Value("${jwt.secret}")
    private  String SECRET_KEY;

    public Map<String,String> generateToken(String username,String type, Object obj) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 60 * 60 * 1000);

        Map<String,Object> claims = new HashMap<>();
        claims.put("type",type);
        claims.put("obj", obj);

        Map<String,String> token = new HashMap<>();
        token.put("token",Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact());

        return token;
    }

//    public String extractEmail(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
//    }

    public Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
