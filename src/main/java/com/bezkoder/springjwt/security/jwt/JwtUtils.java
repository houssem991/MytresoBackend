package com.bezkoder.springjwt.security.jwt;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.*;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Autowired
  public UserRepository userRepository;




  private String JWT_HEADER_NAME = "Authorization";
  private String HEADER_PREFIX = "Bearer ";
  private final String SECRET = "ZwHqm8F3roWRnYjTJe5Zyw==";
  private Long SESSION_EXPIRATION = 1000L * 3600 * 24 * 365;
  public Long Password_EXPIRATION = 1000L * 60 * 5;
  public Integer RESET_PWD_TOKEN_EXPIRATION = 1000 * 60 * 30;
  public Integer SET_PWD_TOKEN_EXPIRATION = 1000 * 3600 * 24 * 5;


  public String[] AUTH_WHITELIST = {
          "/api/auth/**"
  };

  public String generatePinPassword() {
    int m = (int) Math.pow(10, 4 - 1);
    return ""+(m + new Random().nextInt(9 * m));
  }

  public String generateJwtToken(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
            .setSubject((userPrincipal.getUsername()))
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + SESSION_EXPIRATION))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();

  }

  public String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader(JWT_HEADER_NAME);
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(HEADER_PREFIX)) {
      return headerAuth.substring(7);
    } else {
      return null;
    }
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
//        return UserType.AGENT;
  }


  private String getUserIssuerFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getIssuer();
  }

  public boolean  validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage().toString());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage().toString());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage().toString());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage().toString());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage().toString());
    }
    return false;
  }

  public Long getAgentIdFromJwtToken(String token) {

    return Long.valueOf(Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().get("agent_id").toString());
  }

  public Long getAgencyIdFromJwtToken(String token) {

    return Long.valueOf(Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().get("agency_id").toString());
  }

  public Long getEntrepriseIdFromJwtToken(String token) {

    return Long.valueOf(Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().get("entreprise_id").toString());
  }
}