package com.hoaht.auth_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hoaht.auth_service.entities.UserVO;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt.token.secret}")
    private String JWT_SECRET;

    @Value("${jwt.token.access}")
    private String ACCESS_TOKEN;

    @Value("${jwt.token.refresh}")
    private String REFRESH_TOKEN;

    @Value("${jwt.token.temp}")
    private String TEMP_TOKEN;

    @Value("${jwt.token.expiration}")
    private String expirationTime;

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserVO user, Long extraTime) throws JsonProcessingException {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + extraTime);

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(now)
                .claim("deviceId", user.getUuid())
                .claim("fullName", user.getFirstName() + " " + user.getLastName())
                .addClaims(claims)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        if (token.contains("Bearer")) {
            token = getJwtRaw(token);
        }
        try {
            return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        } catch (SignatureException ex) {
            throw new JwtException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtException("JWT claims string is empty");
        }
    }

    public static String getJwtRaw(String authorizationHeader) {
        return authorizationHeader.replaceAll("Bearer", "").trim();
    }

    public Boolean validateToken(String token, UserVO userVO) {
        final String userId = extractClaim(token, Claims::getSubject);

        return (userId.equals(userVO.getId()) && !isTokenExpired(token));
    }

    public static void main(String args[]) {
        UserVO user = new UserVO();
        try {
            user.setId("User1");
            user.setUuid("UUID");
            user.setLastName("LastName");
            user.setFirstName("FirstName");
            JwtUtil jwtUtil = new JwtUtil();
            jwtUtil.JWT_SECRET = "SECRET_KEY_2021";
            String token = jwtUtil.generateToken(user, 10000000000L);
            System.out.println("generateToken: " + token);
            System.out.println("getExpirationDateFromToken: " + jwtUtil.getExpirationDateFromToken(token));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
