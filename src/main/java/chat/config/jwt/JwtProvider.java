package chat.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.token.validity}")
    private long tokenValidity;

    @Value("${jwt.token.secret}")
    private String secret;

    @PostConstruct
    public void init() {
        this.secret = Base64Codec.BASE64.encode(secret);
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + tokenValidity);
    }

    public String generateToken(final String username) {
        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(final String token) {
        try {
            return !getBody(token).getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT token is expired or invalid", e);
        }

        return false;
    }

    public Claims getBody(final String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
