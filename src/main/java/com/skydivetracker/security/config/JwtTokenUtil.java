package com.skydivetracker.security.config;

import com.skydivetracker.skydivers.models.Skydiver;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

  @Value("${jwt.token.secret}")
  private String secretString;
  @Value("${jwt.token.lifespan}")
  private int tokenLifeSpan;

  public String getUsernameFromToken(String token) {
    Claims claims = getAllClaimsFromToken(token);
    return claims.get("username", String.class);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  public Claims getAllClaimsFromToken(String token) {
    SecretKey secretKey = Keys.hmacShaKeyFor(secretString.getBytes());
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

  public Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public String createJwtToken(LinkedHashMap<String, Object> claims) {
    if (claims == null || claims.isEmpty()) return "";
    SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes());
    return Jwts.builder()
        .setClaims(claims)
        .signWith(key)
        .compact();
  }

  public void setSecretString(String secretString) {
    this.secretString = secretString;
  }

  public void setTokenLifeSpan(int lifeSpan) {
    this.tokenLifeSpan = lifeSpan;
  }

  public LinkedHashMap<String, Object> createDefaultClaims(Skydiver skydiver) {
    LinkedHashMap<String, Object> claims = new LinkedHashMap<>();
    claims.put("username", skydiver.getUsername());
    claims.put("exp", new Date(System.currentTimeMillis() + tokenLifeSpan));
    return claims;
  }

}