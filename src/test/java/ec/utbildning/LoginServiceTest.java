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

    @ParameterizedTest
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
}
