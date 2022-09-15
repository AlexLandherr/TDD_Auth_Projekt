package ec.utbildning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    private List<User> userList;
    private LoginService loginService;

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
        loginService = new LoginService(userRepo);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "anna, losen, eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbm5hIiwiUGFzc3dvcmQiOiJsb3NlbiJ9.ohJE-JmNnDiDVBEkWwXh_5vft8-fyiA8lGPRrzid5VlhUDG3nTCpzv9rTGh7wBmPBlqOnwCh9-O05Mz--P1IGQ",
            "berit, 123456, eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiZXJpdCIsIlBhc3N3b3JkIjoiMTIzNDU2In0.0QU8FQJDiTnh7AXALKfNI9HEzol2HGCCjCBaTlW4ToIm9c6Z9DY9icmNhLzgbV10wf5B87RB4EPHjqcXowcfBQ",
            "kalle, password, eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYWxsZSIsIlBhc3N3b3JkIjoicGFzc3dvcmQifQ.5dWlb3zHE13RLXREtT2tLM4d40jpQpRFkEzn7NLB5qbKlFLPVFEf3LPZoD7I4qg5xdfTp3IkM-oTIfQY1Vbsug"
    })
    public void userLogin_CorrectPasswords(String username, String password, String expectedOutcome) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        String result = loginService.userLogin(username, password);

        //Then
        Assertions.assertEquals(expectedOutcome, result);
    }

    @ParameterizedTest
    @MethodSource("ArgumentInputter")
    public void userLogin_IncorrectPasswords(String username, String password, Class<Exception> expectedClass) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        //Then
        Assertions.assertThrows(expectedClass, () -> loginService.userLogin(username, password));
    }

    public static Stream<Arguments> ArgumentInputter() {
        return Stream.of(
                Arguments.of("anna", "correcthorsebatterystaple", InputMismatchException.class),
                Arguments.of("berit", "NCC-1701", InputMismatchException.class),
                Arguments.of("kalle", "hallon", InputMismatchException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("ArgumentInputter1")
    public void userLogin_InCorrectUsername(String username, String password, Class<Exception> expectedClass) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        //Then
        Assertions.assertThrows(expectedClass, () -> loginService.userLogin(username, password));
    }

    public static Stream<Arguments> ArgumentInputter1() {
        return Stream.of(
                Arguments.of("Picard", "losen", InputMismatchException.class),
                Arguments.of("Number One", "123456", InputMismatchException.class),
                Arguments.of("Dr. Crusher", "password", InputMismatchException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("ArgumentInputter2")
    public void userLogin_UsernameAndPasswordIncorrect(String username, String password, Class<Exception> expectedClass) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        //Then
        Assertions.assertThrows(expectedClass, () -> loginService.userLogin(username, password));
    }

    public static Stream<Arguments> ArgumentInputter2() {
        return Stream.of(
                Arguments.of("Data", "Lt. Commander", InputMismatchException.class),
                Arguments.of("LaForge", "Chief Engineer", InputMismatchException.class),
                Arguments.of("Worf", "Chief of Security", InputMismatchException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("ArgumentInputter3")
    public void userLogin_NullUsernameAndPassword(String username, String password, Class<Exception> expectedClass) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        //Then
        Assertions.assertThrows(expectedClass, () -> loginService.userLogin(username, password));
    }

    public static Stream<Arguments> ArgumentInputter3() {
        return Stream.of(
                Arguments.of("", "", InputMismatchException.class),
                Arguments.of("", "", InputMismatchException.class),
                Arguments.of("", "", InputMismatchException.class)
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
            "anna, losen, eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbm5hIiwiUGFzc3dvcmQiOiJsb3NlbiJ9.ohJE-JmNnDiDVBEkWwXh_5vft8-fyiA8lGPRrzid5VlhUDG3nTCpzv9rTGh7wBmPBlqOnwCh9-O05Mz--P1IGQ",
            "berit, 123456, eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiZXJpdCIsIlBhc3N3b3JkIjoiMTIzNDU2In0.0QU8FQJDiTnh7AXALKfNI9HEzol2HGCCjCBaTlW4ToIm9c6Z9DY9icmNhLzgbV10wf5B87RB4EPHjqcXowcfBQ",
            "kalle, password, eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYWxsZSIsIlBhc3N3b3JkIjoicGFzc3dvcmQifQ.5dWlb3zHE13RLXREtT2tLM4d40jpQpRFkEzn7NLB5qbKlFLPVFEf3LPZoD7I4qg5xdfTp3IkM-oTIfQY1Vbsug"
    })
    public void userLogin_TokenConfirmation(String username, String password, String expectedToken) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        String result = loginService.userLogin(username, password);

        //Then
        Assertions.assertEquals(expectedToken, result);
    }
}
