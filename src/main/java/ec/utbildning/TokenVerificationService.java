package ec.utbildning;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.List;
import java.util.Map;

public class TokenVerificationService {
    Key key = Keys.hmacShaKeyFor("WhoseLineIsItAnyway?".repeat(8).getBytes());
    UserRepo userRepo;
    public TokenVerificationService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean tokenCheck(String token) {
        List<String> tokensOfAllUsers = userRepo.findAll()
                .stream()
                .map(user -> Jwts.builder()
                        .setSubject(user.getUsername())
                        .addClaims(Map.of("Password", user.getPassword()))
                        .signWith(key)
                        .compact())
                .toList();
        return tokensOfAllUsers.contains(token);
    }
}
