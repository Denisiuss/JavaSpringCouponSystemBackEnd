package com.jb.projectNo2.Utils;

import com.jb.projectNo2.Beans.UserDetails;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTutil {

    //type of encryption - тип шифрования
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    //our private key - ключ шифрования который существует только у нас
    private String encodedSecretKey = "this+is+my+key+it+must+be+at+least+256+bits+long";
    //create out private key - создание ключа шифрования для создания нашего токена
    private Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), this.signatureAlgorithm);

    //generate key
    //we need user email, password and role for create the token
    //since the userDetail is an instance of class, we need to make it hashcode
    //the token need to get claims which is the information in hashcode
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>(); //create new hashmap for claims
        claims.put("userPass", userDetails.getPassword());//insert password
        claims.put("userType", userDetails.getUserType());//insert userType (role)
        return createToken(claims, userDetails.getEmail());//send the subject (email)
    }

    //we create the JWT token from the information that we got
    private String createToken(Map<String, Object> claims, String email) {
        Instant now = Instant.now(); //get current time
        return Jwts.builder()        //create JWT builder to assist us with JWT creation
                .setClaims(claims)  //set out data (claims - user, password, role, etc..)
                .setSubject(email)  //set our subject, the first item that we will check
                .setIssuedAt(Date.from(now)) // create issued at, which is current time
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES))) // expiration date, which after the date, we need to create a new token
                .signWith(this.decodedSecretKey) //sign the token with our secret key
                .compact(); //compact our token, that it will be smaller
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException{
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(this.decodedSecretKey)
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String extractEmail (String token){
        return extractAllClaims(token).getSubject();
    }

    public java.util.Date extractExpirationDate (String token){
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        try{
            extractAllClaims(token);
            return false;
        } catch (ExpiredJwtException err){
            return true;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String userEmail = extractEmail(token);
        return (userEmail.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }
    //tester

    /*public static void main(String[] args) {
        //create our instance of our user that the token will be created for him
        UserDetails admin = new UserDetails("admin@admin.com", "12345", "Admin");
        //use our new shiny JWTutils
        JWTutil myUtil = new JWTutil();
        //generate new token
        String myToken = myUtil.generateToken(admin);
        //print the token on screen to show to our mama
        System.out.println("my new shiny token:\n"+myToken);
        //get out claims
        System.out.println("our token body is:\n"+myUtil.extractAllClaims(myToken));
        System.out.println("and the winner is:\n"+myUtil.extractEmail(myToken));

    }*/
}
