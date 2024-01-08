package com.example.jwtauth.security;

import com.example.jwtauth.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private String jwtSecret = "batch";

    //24*60*60*1000
    private long jwtExpirationMs = 864000000;

    //************generate token**********
    public String generateToken(Authentication authentication) {
        //anlık olarak login olan kullanıcının bilgisi almak
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //token buildir() ile üretilir
        //token üretilirken UserName ve secret key kullanılır
        return Jwts.builder().setSubject(userDetails.getUsername()).
                setIssuedAt(new Date()).
                setExpiration(new Date(new Date().getTime() + jwtExpirationMs)).
                signWith(SignatureAlgorithm.HS512, jwtSecret).
                compact();

    }

    //************ validate-token**********
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException(e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

    }

    ///**********JWT tokenden userName i alma
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

}
