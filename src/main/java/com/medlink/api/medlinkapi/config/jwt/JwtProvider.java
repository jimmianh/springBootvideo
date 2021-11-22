package com.medlink.api.medlinkapi.config.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.time.LocalDate;

import io.jsonwebtoken.SignatureAlgorithm;

import lombok.Value;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;




@Component
@Log
public class JwtProvider {

    @Value("$(jwt.secret)")
    private String jwtSecret;

    public String generateToken(String login){
        Date date = Date.from(localDate.now().plusDay(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512.jwtSecret)
                .compact();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;

        }catch (Exception e){
            log.severe("invalid token");
        }
        return false;
    }
    public String getLoginFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
