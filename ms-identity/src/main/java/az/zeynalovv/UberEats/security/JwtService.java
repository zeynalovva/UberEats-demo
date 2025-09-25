package az.zeynalovv.UberEats.security;

import az.zeynalovv.UberEats.config.properties.ApplicationProperties;
import az.zeynalovv.UberEats.dto.UserDto;
import az.zeynalovv.UberEats.entity.constant.TokenKey;
import az.zeynalovv.UberEats.entity.enums.TokenType;
import az.zeynalovv.UberEats.util.CryptoUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;


@Service
public class JwtService {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    @SneakyThrows
    public JwtService(ApplicationProperties applicationProperties){
        privateKey = CryptoUtil.loadPrivateKey(applicationProperties.getSecurity()
                .getAuthentication().getPrivateKey());

        publicKey = CryptoUtil.loadPublicKey(applicationProperties.getSecurity()
                .getAuthentication().getPublicKey());
    }

    public String generateToken(UserDto user, TokenType tokenType) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(TokenKey.ID, user.getId())
                .claim(TokenKey.USER_ROLE, user.getUserRole())
                .claim(TokenKey.USER_STATUS, user.getUserStatus())
                .claim(TokenKey.TOKEN_TYPE, tokenType.name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }


    public String extractToken(final HttpServletRequest request, final String headerName){
        String authHeader = request.getHeader(headerName);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.substring(7);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public String extractUser(String token) {
        return extractClaim(token, claims -> claims.get("sub", String.class));
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Map<String, Object> extractAllClaimsAsMap(String token) {
        return extractAllClaims(token);
    }

    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
