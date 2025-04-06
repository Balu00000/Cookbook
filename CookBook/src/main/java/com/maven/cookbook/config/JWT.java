package com.maven.cookbook.config;

import com.maven.cookbook.exception.ExceptionLogger;
import com.maven.cookbook.model.User;
import com.maven.cookbook.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class JWT {
    
    /*
    eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJWaXN1ZWxzZSIsInN1YiI6Im1hbmFnZW1lbnQiLCJpZCI6MTI3LCJzY29wZSI6InVzZXIiLCJzc1RhZyI6IkBBQiIsImlhdCI6MTcyNDk1NzMwOSwiZXhwIjoxNzI1NTYyMTA5fQ.Wj7kKUIVtWpLHwEkghRqnAjkb0xO7GJ4mOpiEDgxrs0
     */
    private static final String SIGN = "09ce78e64c7d6667e04798aa897e2bbc194d0ce5d19aef677b4477ba0932d972";
    private static final byte[] SECRET = Base64.getDecoder().decode(SIGN);
    private static final ExceptionLogger exceptionLogger = new ExceptionLogger(JWT.class);
    private static final UserRepository layer = new UserRepository();
    
    
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
            .signWith(SignatureAlgorithm.HS256,
                Decoders.BASE64.decode(SIGN)
            )
            .compact();

        return token;
    }

    public static int validateJWT(String jwt) {
        try {
            Jws<Claims> result;
            result = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(SECRET)).parseClaimsJws(jwt);
            int id = result.getBody().get("id", Integer.class);
            User u = layer.findUserById(id);

            if (u.getId() == id) {
                return 1;
            } else {
                return 2; //Ez akkor történik amikor egy érvénytelen tokent akarunk validáltatni
            }
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | WeakKeyException | IllegalArgumentException e) {
            exceptionLogger.errorLog(e);
            return 3; //Akkor történik ha lejárt a JWT-k
        }
    }
    
    public static boolean isAdmin(String jwt) {
        Jws<Claims> result; //main függvényböl
        System.out.println(jwt);
        
        result = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(SECRET)).parseClaimsJws(jwt);
        
        Boolean isAdmin = result.getBody().get("isAdmin", Boolean.class);
        return isAdmin;
    }
}
