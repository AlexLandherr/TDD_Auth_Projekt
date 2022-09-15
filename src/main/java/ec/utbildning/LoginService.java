package ec.utbildning;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.InputMismatchException;
import java.util.Map;

public class LoginService {

    Key key = Keys.hmacShaKeyFor("WhoseLineIsItAnyway?".repeat(8).getBytes());
    UserRepo userRepo;
    public LoginService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String userLogin(String username, String password) {
        String token;
        boolean isMatch = userRepo.findAll()
                .stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
        if (isMatch) {
            token = Jwts.builder()
                    .setSubject(username)
                    .addClaims(Map.of("Password", password))
                    .signWith(key)
                    .compact();
        } else {
            throw new InputMismatchException("Wrong username or password! Please try again.");
        }
        return token;
    }
}
