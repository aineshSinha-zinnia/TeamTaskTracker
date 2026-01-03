package com.ainesh.TeamTaskTracker.services.securityServices;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.interfaces.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

  private final String secretKey;

  public JwtServiceImpl(@Value("${jwt.secret}") String secretKeyString){
    this.secretKey = secretKeyString;
  }

  private SecretKey getKey(){
    byte[] keyArray = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyArray);
  }

  @Override
  public String generateToken(String email) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
               .claims()
               .add(claims)
               .subject(email)
               .issuedAt(Date.from(Instant.now()))
               .expiration(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)))
               .and()
               .signWith(getKey())
               .compact();
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token){
    return Jwts.parser()
               .verifyWith(getKey())
               .build()
               .parseSignedClaims(token)
               .getPayload();
  }

  @Override
  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private Boolean isTokenExpired(String token) {
    return extractExpirtyDate(token).before(new Date());
  }

  private Date extractExpirtyDate(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  @Override
  public Boolean validateToken(String token, UserDetails userDetails) {
    String username = extractEmail(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }
  
}
