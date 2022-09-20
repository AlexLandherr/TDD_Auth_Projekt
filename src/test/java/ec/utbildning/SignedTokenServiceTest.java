package ec.utbildning;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SignedTokenServiceTest {
    private List<User> userList;
    private SignedTokenService signedTokenService;
    Key secret = new SecretKeySpec("WhoseLineIsItAnyway?".repeat(8).getBytes(),
            SignatureAlgorithm.HS256.getJcaName());

    @Mock
    UserRepo userRepo;

    @BeforeEach
    public void setUserList() {
        userList = List.of(
                new User("anna", "losen", UserRole.STUDENT),
                new User("berit", "123456", UserRole.TEACHER),
                new User("kalle", "password", UserRole.ADMIN)
        );
    }

    @BeforeEach
    public void setUp() {
        signedTokenService = new SignedTokenService(userRepo);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "anna, STUDENT",
            "berit, TEACHER",
            "kalle, ADMIN"
    })
    public void correct_SignedToken(String username, String userRole) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        String result = signedTokenService.signedToken(username, userRole);

        //Then
        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(result);

        Assertions.assertEquals(username, jwt.getBody().getSubject());
        Assertions.assertEquals(userRole, jwt.getBody().get("UserRole", String.class));
    }

    @ParameterizedTest
    @MethodSource("ArgumentInputter")
    public void IncorrectUserRole_SignedToken(String username, String userRole, Class<Exception> expectedClass) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        //Then
        Assertions.assertThrows(expectedClass, () -> signedTokenService.signedToken(username, userRole));
    }

    public static Stream<Arguments> ArgumentInputter() {
        return Stream.of(
                Arguments.of("anna", "CLASS_CLOWN", InputMismatchException.class),
                Arguments.of("berit", "JANITOR", InputMismatchException.class),
                Arguments.of("kalle", "BESSERWISSER", InputMismatchException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("ArgumentInputter1")
    public void IncorrectUsername_SignedToken(String username, String userRole, Class<Exception> expectedClass) {
        //Given

        //When
        //Then
        Assertions.assertThrows(expectedClass, () -> signedTokenService.signedToken(username, userRole));
    }

    public static Stream<Arguments> ArgumentInputter1() {
        return Stream.of(
                Arguments.of("Wesley Crusher", "STUDENT", InputMismatchException.class),
                Arguments.of("Geordi LaForge", "TEACHER", InputMismatchException.class),
                Arguments.of("Jean-Luc Picard", "ADMIN", InputMismatchException.class)
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
            "anna, STUDENT",
            "berit, TEACHER",
            "kalle, ADMIN"
    })
    public void correct_UserRole(String username, String expectedUserRole) {
        //Given
        when(userRepo.findByUsername()).thenReturn(userList);
        /*when(userRepo.findByUsername()).thenReturn(
                userList.stream()
                        .filter(user -> user.getUsername().equals(username))
                        .findAny()
                        .orElse(null)
        );*/

        //When
        String result = signedTokenService.userRole(username);

        //Then
        Assertions.assertEquals(expectedUserRole, result);
    }

    @ParameterizedTest
    @MethodSource("ArgumentInputter2")
    public void IncorrectUsername_UserRole(String username, Class<Exception> expectedClass) {
        //Given

        //When
        //Then
        Assertions.assertThrows(expectedClass, () -> signedTokenService.userRole(username));
    }

    public static Stream<Arguments> ArgumentInputter2() {
        return Stream.of(
                Arguments.of("Pavel Chekov", InputMismatchException.class),
                Arguments.of("Mr. Spock", InputMismatchException.class),
                Arguments.of("James T. Kirk", InputMismatchException.class)
        );
    }
}
