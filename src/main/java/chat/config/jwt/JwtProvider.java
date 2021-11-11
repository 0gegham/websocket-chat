package chat.config.jwt;

import io.jsonwebtoken.*;
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
            return !getClaims(token).getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT token is expired or invalid");
        }

        return false;
    }

    public Jws<Claims> getClaims(final String token) throws JwtException {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
    }

    public long getExpirationDateTime(final String token) {
        return getClaims(token).getBody().getExpiration().getTime();
    }
}
