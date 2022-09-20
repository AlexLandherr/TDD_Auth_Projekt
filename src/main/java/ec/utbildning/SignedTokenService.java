package ec.utbildning;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.UUID;

public class SignedTokenService {
    Key secret = new SecretKeySpec("WhoseLineIsItAnyway?".repeat(8).getBytes(),
            SignatureAlgorithm.HS256.getJcaName());
    UserRepo userRepo;

    public SignedTokenService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String signedToken(String username, String userRole) {
        String token;
        boolean isMatch = userRepo.findAll()
                .stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getUserRoleEnum().toString().equals(userRole));
        if (isMatch) {
            token = Jwts.builder()
                    .setSubject(username)
                    .addClaims(Map.of("UserRole", userRole))
                    .setId(UUID.randomUUID().toString())
                    .signWith(secret)
                    .compact();
        } else {
            throw new InputMismatchException("Wrong username or UserRole! Please try again.");
        }
        return token;
    }

    public String userRole(String username) {
        User returnedUser;
        boolean isMatch = userRepo.findByUsername().stream()
                .anyMatch(user -> user.getUsername().equals(username));
        if (isMatch) {
            returnedUser = userRepo.findByUsername().stream()
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);
        } else {
            throw new InputMismatchException("Username does not exist! Please try again.");
        }
        assert returnedUser != null;
        return returnedUser.getUserRoleEnum().toString();
    }
}
