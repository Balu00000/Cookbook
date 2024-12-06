/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chef.cookbook.config;

import com.chef.cookbook.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class JWT {
    private static String sign = "09ce78e64c7d6667e04798aa897e2bbc194d0ce5d19aef677b4477ba0932d972";
    private static byte[] secret = Base64.getDecoder().decode(sign);
    
    public static String createJWT(User u) {
        Instant now = Instant.now();

        String token = Jwts.builder()
                .setIssuer("IAKK")
                .setSubject("valamit")
                .claim("id", u.getId())
                .claim("isAdmin", u.getIsAdmin())
                .claim("createdAt", u.getCreatedAt())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.DAYS)))
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(sign)
                )
                .compact();

        return token;
    }
    
    public static int validateJWT(String jwt) {
        try {
            Jws<Claims> result;
            result = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(secret)).parseClaimsJws(jwt);
            int id = result.getBody().get("id", Integer.class);
            User u = new User(id);

            if (u.getId() == id) {
                return 1;
            } else {
                return 2; //Ez akkor történik amikor egy érvénytelen tokent akarunk validáltatni
            }
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | WeakKeyException | IllegalArgumentException e) {
            System.err.println("JWT validation error: " + e.getLocalizedMessage());
            return 3; //Akkor történik ha lejárt a JWT-k
        }

    }
}
