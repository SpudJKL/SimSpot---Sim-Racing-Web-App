package com.SimSpot.ecommerce.security;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

/**
 * This class JwtUtils is used to generate and create JWT tokens used for
 * authentication of the user
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${SimSpot.app.jwtSecret}")
    private String jwtSecret;

    @Value("${SimSpot.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * This method generateJwtToken, takes the spring security interface
     * authentication as a parameter. The method returns a new JwtBuilder with
     * all the users details being set using the JwtBuilder methods.
     * @param authentication
     * @return
     */
    public String generateJwtToken(Authentication authentication) {
        // This sets the User to be authenticated
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        // Returns JwtBuilder object
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * This method getUserNameFromJwtToken takes a String token as a param
     * the method returns the Username set for the given token
     * @param token
     * @return
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * This method validateJwtToken takes a String authToken as param
     * this method will return true if the token is validated successfully,
     * if false then it will accurately display what is wrong with the authToken
     * @param authToken
     * @return
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}