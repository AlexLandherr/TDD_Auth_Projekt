package ec.utbildning;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Map;
import java.util.UUID;

public class SignedTokenTest {
    User testUser = new User("anna", "losen", UserRole.STUDENT);
    Key secret = new SecretKeySpec("WhoseLineIsItAnyway?".repeat(8).getBytes(),
            SignatureAlgorithm.HS256.getJcaName());

    @Test
    public void SignedToken() {
        //Creating signed test token
        String jwtToken = Jwts.builder()
                .setSubject(testUser.getUsername())
                .addClaims(Map.of("UserRole", testUser.getUserRoleEnum()))
                .setId(UUID.randomUUID().toString())
                .signWith(secret)
                .compact();

        //Validating/parsing signed test token
        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(jwtToken);

        System.out.println("Generated signed JWT:\n" + jwtToken);
        System.out.println("Parsed JWT:\n" + jwt);
    }
}
