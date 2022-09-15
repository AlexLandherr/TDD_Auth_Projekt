package ec.utbildning;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Map;

public class TokenTestDemo {
    String username = "kalle";
    String password = "password";
    //String token;
    String parsedTokenUsername;
    String parsedTokenPassword;

    Key key = Keys.hmacShaKeyFor("WhoseLineIsItAnyway?".repeat(8).getBytes());

    @Test
    public void createJwtToken() {
        String token = Jwts.builder()
                .setSubject(username)
                .addClaims(Map.of("Password", password))
                .signWith(key)
                .compact();

        System.out.println(token);
    }

    /*@Test
    public void parseJwtToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbm5hIiwiUGFzc3dvcmQiOiJsb3NlbiJ9.ohJE-JmNnDiDVBEkWwXh_5vft8-fyiA8lGPRrzid5VlhUDG3nTCpzv9rTGh7wBmPBlqOnwCh9-O05Mz--P1IGQ";

        parsedTokenUsername = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        parsedTokenPassword = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("Password", String.class);

        System.out.println("Username: " + parsedTokenUsername + "\n" + "Password: " + parsedTokenPassword);
    }*/
}
