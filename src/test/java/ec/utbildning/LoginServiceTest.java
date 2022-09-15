package ec.utbildning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

    /*@ParameterizedTest
    @CsvSource(value = {
            "anna, losen, true",
            "berit, 123456, true",
            "kalle, password, true"
    })
    public void userLogin_CorrectPasswords(String username, String password, boolean expectedOutcome) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        boolean result = loginService.userLogin(username, password);

        //Then
        Assertions.assertEquals(expectedOutcome, result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "anna, correcthorsebatterystaple, false",
            "berit, NCC-1701, false",
            "kalle, hallon, false"
    })
    public void userLogin_IncorrectPasswords(String username, String password, boolean expectedOutcome) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        boolean result = loginService.userLogin(username, password);

        //Then
        Assertions.assertEquals(expectedOutcome, result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Picard, losen, false",
            "Number One, 123456, false",
            "Dr. Crusher, password, false"
    })
    public void userLogin_IncorrectUsername(String username, String password, boolean expectedOutcome) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        boolean result = loginService.userLogin(username, password);

        //Then
        Assertions.assertEquals(expectedOutcome, result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Data, Lt. Commander, false",
            "LaForge, Chief Engineer, false",
            "Worf, Chief of Security, false"
    })
    public void userLogin_UsernameAndPasswordIncorrect(String username, String password, boolean expectedOutcome) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        boolean result = loginService.userLogin(username, password);

        //Then
        Assertions.assertEquals(expectedOutcome, result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            ",, false",
            ",, false",
            ",, false"
    })
    public void userLogin_NullUsernameAndPassword(String username, String password, boolean expectedOutcome) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        boolean result = loginService.userLogin(username, password);

        //Then
        Assertions.assertEquals(expectedOutcome, result);
    }*/

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
