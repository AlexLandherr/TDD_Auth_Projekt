package ec.utbildning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenVerificationServiceTest {
    private List<User> userList;
    private TokenVerificationService tokenVerificationService;

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
        tokenVerificationService = new TokenVerificationService(userRepo);
    }

    //anna
    //berit
    //kalle
    @ParameterizedTest
    @CsvSource(value = {
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbm5hIiwiUGFzc3dvcmQiOiJsb3NlbiJ9.ohJE-JmNnDiDVBEkWwXh_5vft8-fyiA8lGPRrzid5VlhUDG3nTCpzv9rTGh7wBmPBlqOnwCh9-O05Mz--P1IGQ, true",
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiZXJpdCIsIlBhc3N3b3JkIjoiMTIzNDU2In0.0QU8FQJDiTnh7AXALKfNI9HEzol2HGCCjCBaTlW4ToIm9c6Z9DY9icmNhLzgbV10wf5B87RB4EPHjqcXowcfBQ, true",
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYWxsZSIsIlBhc3N3b3JkIjoicGFzc3dvcmQifQ.5dWlb3zHE13RLXREtT2tLM4d40jpQpRFkEzn7NLB5qbKlFLPVFEf3LPZoD7I4qg5xdfTp3IkM-oTIfQY1Vbsug, true"
    })
    public void correctToken(String token, boolean expectedOutcome) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        boolean result = tokenVerificationService.tokenCheck(token);

        //Then
        Assertions.assertEquals(expectedOutcome, result);
    }

    //anna
    //berit
    //kalle
    @ParameterizedTest
    @CsvSource(value = {                                  //↓ arrow points to where the tokens were changed.
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbm5hIiwiUGFzc3avcmQiOiJsb3NlbiJ9.ohJE-JmNnDiDVBEkWwXh_5vft8-fyiA8lGPRrzid5VlhUDG3nTCpzv9rTGh7wBmPBlqOnwCh9-O05Mz--P1IGQ, false",
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiZXJpdCIsIlBhc3b3b3JkIjoiMTIzNDU2In0.0QU8FQJDiTnh7AXALKfNI9HEzol2HGCCjCBaTlW4ToIm9c6Z9DY9icmNhLzgbV10wf5B87RB4EPHjqcXowcfBQ, false",
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYWxsZSIsIlBhc3c3b3JkIjoicGFzc3dvcmQifQ.5dWlb3zHE13RLXREtT2tLM4d40jpQpRFkEzn7NLB5qbKlFLPVFEf3LPZoD7I4qg5xdfTp3IkM-oTIfQY1Vbsug, false"
    })
    public void IncorrectToken(String token, boolean expectedOutcome) {
        //Given
        when(userRepo.findAll()).thenReturn(userList);

        //When
        boolean result = tokenVerificationService.tokenCheck(token);

        //Then
        Assertions.assertEquals(expectedOutcome, result);
    }
}
